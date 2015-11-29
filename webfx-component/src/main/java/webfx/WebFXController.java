package webfx;

/**
 */
public abstract class WebFXController {

    protected Object context;
    protected NavigationContext navigationContext;

    public void setContexts(Object context, NavigationContext navigationContext) {
        this.context = context;
        this.navigationContext = navigationContext;
    }

    public abstract void onShow();

}
