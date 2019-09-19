/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.functions.xml;

import lucee.runtime.PageContext;
import lucee.runtime.exp.FunctionException;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.Function;
import lucee.runtime.net.rpc.client.WSClient;

/**
 * 
 */
public final class GetSOAPResponseHeader implements Function {

    private static final long serialVersionUID = 4667361359302875802L;

    public static Object call(PageContext pc, Object webservice, String namespace, String name) throws PageException {
	return call(pc, webservice, namespace, name, false);
    }

    public static Object call(PageContext pc, Object webservice, String namespace, String name, boolean asXML) throws PageException {
	if (!(webservice instanceof WSClient))
	    throw new FunctionException(pc, "getSOAPResponse", 1, "webservice", "value must be a webservice Object generated with createObject/<cfobject>");
	return ((WSClient) webservice).getSOAPResponseHeader(pc, namespace, name, asXML);
    }
}