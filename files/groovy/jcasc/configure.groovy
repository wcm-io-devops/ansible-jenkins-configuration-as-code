/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2018 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.jenkins.plugins.casc.*
import jenkins.model.*

CasCGlobalConfig config = GlobalConfiguration.all().get(CasCGlobalConfig.class);

Boolean changed=false
String newConfigPath = "$jcasc_path"

if(config != null) {
  String currentConfigPath = config.getConfigurationPath()
  if (currentConfigPath != newConfigPath) {
    changed = true
    config.setConfigurationPath(newConfigPath)
    config.save()
  }
}

return changed
