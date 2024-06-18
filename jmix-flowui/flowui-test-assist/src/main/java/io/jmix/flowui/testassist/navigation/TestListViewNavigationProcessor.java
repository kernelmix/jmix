/*
 * Copyright 2022 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.flowui.testassist.navigation;

import com.vaadin.flow.function.SerializableConsumer;
import io.jmix.flowui.sys.ViewSupport;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewRegistry;
import io.jmix.flowui.view.navigation.*;

import java.net.URL;

/**
 * The main goal of this class is supporting backward navigation in UI integration tests.
 * <p>
 * The {@link ListViewNavigationProcessor} is used for preparing and performing navigation to the
 * inheritor of {@link StandardListView}.
 * <p>
 * To perform navigation the {@link AbstractNavigationProcessor#processNavigation(AbstractViewNavigator)} method
 * checks whether backward navigation should be used or not. To get correct backward navigation URL it uses
 * information from client-side.
 * <p>
 * In UI integration tests there is no client-side, so backward navigation URL should be got by another way.
 * This is why {@link TestListViewNavigationProcessor} replaces {@link ListViewNavigationProcessor} and
 * delegates building backward navigation URL to {@link ViewNavigationDelegate}.
 */
public class TestListViewNavigationProcessor extends ListViewNavigationProcessor {

    protected ViewNavigationDelegate<ListViewNavigator<?>> navigationDelegate;

    public TestListViewNavigationProcessor(ViewSupport viewSupport, ViewRegistry viewRegistry,
                                           ViewNavigationSupport navigationSupport,
                                           ViewNavigationDelegate<ListViewNavigator<?>> navigationDelegate) {
        super(viewSupport, viewRegistry, navigationSupport);

        this.navigationDelegate = navigationDelegate;
    }

    @Override
    public void processNavigation(ListViewNavigator<?> navigator) {
        super.processNavigation(navigator);

        navigationDelegate.saveCurrentNavigation(
                getViewClass(navigator),
                getRouteParameters(navigator),
                getQueryParameters(navigator));
    }

    @Override
    protected void fetchCurrentURL(SerializableConsumer<URL> callback) {
        callback.accept(navigationDelegate.fetchCurrentUrl());
    }
}
