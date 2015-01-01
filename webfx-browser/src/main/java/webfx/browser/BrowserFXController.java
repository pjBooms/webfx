/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package webfx.browser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import webfx.browser.settings.SettingsController;
import webfx.browser.urlhandlers.URLHandler;
import webfx.browser.urlhandlers.URLHandlersRegistry;
import webfx.browser.util.FXUtil;

/**
 *
 * @author Bruno Borges at oracle.com
 */
public class BrowserFXController implements TabManager {

    private static final Logger LOGGER = Logger.getLogger(BrowserFXController.class.getName());
    private static final String HOME_PAGE = "http://javarestart.com";
//    private static final String HOME_PAGE = "http://localhost:8080";

    /**
     * Components
     */
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField urlField;
    @FXML
    private MenuItem closeTab;
    @FXML
    private Button stopButton;
    @FXML
    private Button reloadButton;
    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;
    @FXML
    private Button homeButton;
    /**
     * Internal
     */
    private SingleSelectionModel<Tab> selectionTab;
    private final ConcurrentHashMap<Integer, BrowserTab> browserMap = new ConcurrentHashMap<>();
    private Locale locale;

    public void exit() {
        LOGGER.info("Exiting...");
        System.exit(0);
    }

    public void home() {
        openPage(HOME_PAGE);
    }

    public void newTab() {
        Tab tab = new Tab("New tab");
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
        selectionTab.selectLast();
        focusAddressBar();
    }
    
    void focusAddressBar(){
        urlField.requestFocus();
    }
    
    public void openNetworkSettings(){
        final FXMLLoader settings = FXUtil.load(SettingsController.class);
        try{
            final Node node = settings.load();
            final SettingsController controller = settings.getController();
            final Stage stage = new Stage();
            final Scene scene = new Scene(new Group(node));
            stage.setTitle("Network Settings");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            controller.setOnClose(e -> stage.close());
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(BrowserFXController.class.getName()).log(Level.SEVERE, "Unable to open settings", ex);
        }
    }

    public void stop() {
        selectedBrowserTab().stop();
    }

    public void reload() {
        selectedBrowserTab().getNavigationContext().reload();
    }

    public void back() {
        selectedBrowserTab().getNavigationContext().back();
    }

    public void forward() {
        selectedBrowserTab().getNavigationContext().forward();
    }

    public void closeTab() {
        LOGGER.info("Closing Tab...");
        if (tabPane.getTabs().size() > 1) {
            int indexBrowserTab = selectionTab.getSelectedIndex();
            browserMap.remove(indexBrowserTab);
            tabPane.getTabs().remove(selectionTab.getSelectedIndex());
        }
    }

    public void openFXPage() {
        openPage(urlField.getText());
    }

    public void openPage(String location) {
        URL url;
        try {
            url = URLVerifier.verifyURL(location);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BrowserFXController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        URLHandler urlHandler = URLHandlersRegistry.getHandler(url);
        Platform.runLater(() -> {
            BrowserTab browserTab = urlHandler.handle(url, locale);
            if (browserTab != null) {
                browserTab.setTabManager(this);
                selectionTab.getSelectedItem().contentProperty().bind(browserTab.contentProperty());
                browserMap.put(selectionTab.getSelectedIndex(), browserTab);
                if(!urlField.isFocused()){
                    urlField.textProperty().bind(browserTab.locationProperty());
                }
                stopButton.disableProperty().set(!browserTab.isStoppable());
                selectionTab.getSelectedItem().textProperty().bind(browserTab.titleProperty());
            }
        });
    }

    public void initialize() {
        urlField.focusedProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                urlField.textProperty().unbind();
            } else if (selectedBrowserTab() != null) {
                urlField.textProperty().bind(selectedBrowserTab().locationProperty());
            }
        });

        tabPane.getTabs().addListener((ListChangeListener.Change<? extends Tab> change) -> {
            ObservableList<? extends Tab> tabs = change.getList();

            // disabled the close tab menu item if selected tab is not cloeable
            closeTab.disableProperty().bind(selectionTab.getSelectedItem().closableProperty().not());

            // set the first tab closeable if more than one tab
            tabs.get(0).setClosable(tabs.size() > 1);

            // set others tab closeable, if they exist
            for (int i = 1; i < tabs.size(); i++) {
                tabs.get(i).setClosable(true);
            }
        });

        selectionTab = tabPane.selectionModelProperty().getValue();

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            LOGGER.info("Tab selection changed");
            if (selectedBrowserTab() == null) {
                LOGGER.info("No tab selected");
                urlField.textProperty().unbind();
                urlField.textProperty().setValue("");
                urlField.setText("");
            } else {
                LOGGER.info("There's a tab selected");
                urlField.textProperty().bind(selectedBrowserTab().locationProperty());
            }
        });

        final int size = 16;
        setButtonIcon(stopButton, "stop", size);
        setButtonIcon(backButton, "left", size);
        setButtonIcon(forwardButton, "right", size);
        setButtonIcon(reloadButton, "clock", size);
        setButtonIcon(homeButton, "home", size);
    }

    private void setButtonIcon(Button button, String icon, int size) {
        InputStream is = getClass().getResourceAsStream("icons/" + icon + "_" + size + ".png");
        Image block = new Image(is);
        ImageView iv = new ImageView(block);
        button.setGraphic(iv);
    }

    private BrowserTab selectedBrowserTab() {
        return browserMap.get(selectionTab.getSelectedIndex());
    }

    @Override
    public void openInNewTab(URL url) {
        newTab();
        openPage(url.toString());
    }

    void setLocale(Locale locale) {
        this.locale = locale;
    }

}
