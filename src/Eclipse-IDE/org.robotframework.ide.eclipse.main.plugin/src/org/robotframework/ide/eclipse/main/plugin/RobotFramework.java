package org.robotframework.ide.eclipse.main.plugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class RobotFramework extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.robotframework.ide.eclipse.main.plugin";

    static ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

    public static RobotModelManager getModelManager() {
        return RobotModelManager.getInstance();
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        super.stop(context);
        RobotModelManager.getInstance().dispose();
    }
}
