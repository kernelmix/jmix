/*
 * Copyright 2024 Haulmont.
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

package autowire.fragment.view;

import autowire.fragment.LifecycleEventsDependencyInjectorFragment;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route("autowire-fragment-lifecycle-events-host-view")
@ViewController("AutowireFragmentLifecycleEventsHostView")
@ViewDescriptor("autowire-fragment-lifecycle-events-host-view.xml")
public class AutowireFragmentLifecycleEventsHostView extends StandardView {

    @ViewComponent
    public LifecycleEventsDependencyInjectorFragment fragment;

    @Subscribe
    protected void onInit(InitEvent event) {
        fragment.getExecutedEvents().add("Host." + event.getClass().getSimpleName());
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        fragment.getExecutedEvents().add("Host." + event.getClass().getSimpleName());
    }

    @Subscribe
    protected void onReady(ReadyEvent event) {
        fragment.getExecutedEvents().add("Host." + event.getClass().getSimpleName());
    }
}
