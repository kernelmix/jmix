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

package autowire.fragment

import autowire.fragment.view.AutowireFragmentHostView
import com.vaadin.flow.data.renderer.ComponentRenderer
import io.jmix.flowui.Fragments
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import test_support.spec.FlowuiTestSpecification

@SpringBootTest
class FragmentSupplyDependencyInjectorTest extends FlowuiTestSpecification {

    @Autowired
    Fragments fragments

    @Override
    void setup() {
        registerViewBasePackages("autowire.fragment.view")
    }

    def "Autowire renderers into the fragment"() {
        def hostView = navigateToView AutowireFragmentHostView

        when: "Fragment component is created"
        def fragment = fragments.create hostView, SupplyDependencyInjectorFragment

        then: "The renderers will be set"
        fragment.dataGrid.getColumnByKey("name").getRenderer() instanceof ComponentRenderer
        fragment.component.getItemRenderer() instanceof ComponentRenderer
    }
}