/*
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package common;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.parsers.SAXParserFactory;

/*
 * @test
 * @bug 6723276
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm -DrunSecMngr=true common.Bug6723276Test
 * @run testng/othervm common.Bug6723276Test
 * @summary Test JAXP class can be loaded by bootstrap classloader.
 */
@Listeners({jaxp.library.BasePolicy.class})
public class Bug6723276Test {

    @Test
    public void test1() {
        try {
            SAXParserFactory.newInstance();
        } catch (Exception e) {
            if (e.getMessage().indexOf("org.apache.xerces.jaxp.SAXParserFactoryImpl not found") > 0) {
                Assert.fail(e.getMessage());
            }
        }
    }

    @Test
    public void test2() {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader());
            System.out.println(ClassLoader.getSystemClassLoader().getParent());
            Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[0], ClassLoader.getSystemClassLoader().getParent()));
            SAXParserFactory.newInstance();
        } catch (Exception e) {
            if (e.getMessage().indexOf("org.apache.xerces.jaxp.SAXParserFactoryImpl not found") > 0) {
                Assert.fail(e.getMessage());
            }
        }
    }

}
