/*
 * Copyright 2012 the original author or authors.
 *
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
 */

package griffon.plugins.hypergraphdb

import griffon.util.CallableWithArgs
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Andres Almiray
 */
final class HypergraphdbEnhancer {
    private static final Logger LOG = LoggerFactory.getLogger(HypergraphdbEnhancer)

    private HypergraphdbEnhancer() {}

    static void enhance(MetaClass mc, HypergraphdbProvider provider = HyperGraphHolder.instance) {
        if(LOG.debugEnabled) LOG.debug("Enhancing $mc with $provider")
        mc.withHyperGraph = {Closure closure ->
            provider.withHyperGraph('default', closure)
        }
        mc.withHyperGraph << {String databaseName, Closure closure ->
            provider.withHyperGraph(databaseName, closure)
        }
        mc.withHyperGraph << {CallableWithArgs callable ->
            provider.withHyperGraph('default', callable)
        }
        mc.withHyperGraph << {String databaseName, CallableWithArgs callable ->
            provider.withHyperGraph(databaseName, callable)
        }
    }
}
