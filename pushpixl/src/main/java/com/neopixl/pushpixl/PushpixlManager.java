package com.neopixl.pushpixl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.neopixl.pushpixl.exception.IncorrectConfigurationException;
import com.neopixl.pushpixl.model.PushConfiguration;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class PushpixlManager {

    private static PushpixlManager _instance;

    /**
     * Retreive the current instance of the manager installed
     *
     * @return a PushpixlManager
     */
    public static PushpixlManager getInstance() {
        if (_instance == null) {
            throw new IncorrectConfigurationException("Cannot use Pushpixl without installing the configuration `install(context, configuration)`");
        }
        return _instance;
    }

    /**
     * Install the given configuration as the default PushpixlManager
     *
     * @param context the application context
     * @param configuration the configuration for the manager
     * @return the new PushpixlManager
     */
    public static PushpixlManager install(@NonNull Context context, @NonNull PushConfiguration configuration) {
        PushpixlManager pushpixlManager = new PushpixlManager(context, configuration);
        _instance = pushpixlManager;
        return getInstance();
    }


    @NonNull private Context context;
    @NonNull private PushConfiguration configuration;

    /**
     * Default constructor for installation
     *
     * @param context the application context
     * @param configuration the configuration for the manager
     */
    private PushpixlManager(Context context, PushConfiguration configuration) {
        this.context = context.getApplicationContext();
        this.configuration = configuration;
    }
}
