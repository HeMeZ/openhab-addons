/**
 * Copyright (c) 2010-2019 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.lgwebos.internal;

import static org.openhab.binding.lgwebos.LGWebOSBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.lgwebos.handler.LGWebOSHandler;
import org.openhab.binding.lgwebos.internal.discovery.LGWebOSDiscovery;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link LGWebOSHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Sebastian Prehn - initial contribution
 */
@NonNullByDefault
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.lgwebos")
public class LGWebOSHandlerFactory extends BaseThingHandlerFactory {
    private @Nullable LGWebOSDiscovery discovery;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Reference
    protected void bindDiscovery(LGWebOSDiscovery discovery) {
        this.discovery = discovery;
    }

    protected void unbindDiscovery(LGWebOSDiscovery discovery) {
        this.discovery = null;
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        LGWebOSDiscovery lgWebOSDiscovery = discovery;
        if (lgWebOSDiscovery == null) {
            throw new IllegalStateException("LGWebOSDiscovery must be bound before ThingHandlers can be created");
        }
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        if (thingTypeUID.equals(THING_TYPE_WEBOSTV)) {
            return new LGWebOSHandler(thing, lgWebOSDiscovery.getDiscoveryManager());
        }
        return null;
    }
}
