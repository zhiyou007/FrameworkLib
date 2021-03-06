package com.wzm.framework.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络判断工具（有网（WIFI OR MOBILE）/无网）
 */
public class NetworkTools {

    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;

    public static int getNetworkState(Context context){
    	if(context==null)
    	{
    		return NETWORN_NONE;
    	}
    	
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager==null)
        {
        	return NETWORN_NONE;
        }
        
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(state == State.CONNECTED||state == State.CONNECTING){
            return NETWORN_WIFI;
        }
        
        if(isNetworkAvailable(context))
        {
        	return NETWORN_MOBILE;
        }
        return NETWORN_NONE;
    }
    
    
    /**
    * 网络是否可用
    *
    * @param context
    * @return
    */
    public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (connectivity == null) {
	    
	    } else {
		    NetworkInfo[] info = connectivity.getAllNetworkInfo();
			    if (info != null) {
				    for (int i = 0; i < info.length; i++) {
					    if (info[i].getState() == State.CONNECTED) {
					    	return true;
				    }
			    }
		    }
	    }
	    return false;
    }

}
