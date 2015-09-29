package com.plego.wagerocity.android;

import com.plego.wagerocity.SystemServiceModule;
import com.plego.wagerocity.android.adapters.PoolsListAdapter;
import com.plego.wagerocity.android.fragments.*;
import com.plego.wagerocity.android.services.ApiModule;
import com.plego.wagerocity.android.services.GetUserService;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
@Component(modules = {UiModule.class, SystemServiceModule.class, ApiModule.class})
@Singleton
public interface BaseComponent {

	void inject (CreatePoolFragment fragment);
    void inject(BetOnGameFragment fragment);

	void inject (PoolsListAdapter adapter);

	void inject (PoolsFragment fragment);

	void inject (MyPoolsFragment fragment);

	void inject (GetUserService service);
}
