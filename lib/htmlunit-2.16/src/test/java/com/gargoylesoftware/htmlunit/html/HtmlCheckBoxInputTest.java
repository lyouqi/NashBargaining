/*
 * Copyright (c) 2002-2015 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.html;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.BrowserRunner;
import com.gargoylesoftware.htmlunit.SimpleWebTestCase;

/**
 * Tests for {@link HtmlCheckBoxInput}.
 *
 * @version $Revision: 9868 $
 * @author Mike Bresnahan
 * @author Marc Guillemot
 * @author Ahmed Ashour
 */
@RunWith(BrowserRunner.class)
public class HtmlCheckBoxInputTest extends SimpleWebTestCase {

    /**
     * Tests onclick event handlers. Given an onclick handler that does not cause the form to submit, this test
     * verifies that HtmlCheckBix.click():
     * <ul>
     *   <li>sets the checkbox to the "checked" state</li>
     *   <li>returns the same page</li>
     * </ul>
     * @throws Exception if the test fails
     */
    @Test
    public void onClick() throws Exception {
        final String html
            = "<html><head><title>foo</title></head><body>\n"
            + "<form id='form1' onSubmit='alert(\"bar\")' onReset='alert(\"reset\")'>\n"
            + "    <input type='checkbox' name='checkbox' id='checkbox' "
            + "onClick='alert(\"foo\");alert(event.type);'>Check me</input>\n"
            + "</form></body></html>";

        final List<String> collectedAlerts = new ArrayList<>();
        final HtmlPage page = loadPage(html, collectedAlerts);
        final HtmlCheckBoxInput checkBox = page.getHtmlElementById("checkbox");
        final HtmlPage secondPage = checkBox.click();

        final String[] expectedAlerts = {"foo", "click"};
        assertEquals(expectedAlerts, collectedAlerts);

        assertSame(page, secondPage);
        assertTrue(checkBox.isChecked());
    }

    /**
     * Tests onclick event handlers. Given an onclick handler that causes the form to submit, this test
     * verifies that HtmlCheckBix.click():
     * <ul>
     *   <li>sets the checkbox to the "checked" state</li>
     *   <li>returns the new page</li>
     * </ul>
     * @throws Exception if the test fails
     */
    @Test
    public void onClickThatSubmitsForm() throws Exception {
        final String html
            = "<html><head><title>foo</title></head><body>\n"
            + "<form id='form1' name='form1'>\n"
            + "    <input type='checkbox' name='checkbox' id='checkbox' "
            + "onClick='document.form1.submit()'>Check me</input>\n"
            + "</form></body></html>";
        final HtmlPage page = loadPage(html);
        final HtmlCheckBoxInput checkBox = page.getHtmlElementById("checkbox");

        final HtmlPage secondPage = checkBox.click();

        assertNotSame(page, secondPage);
        assertTrue(checkBox.isChecked());
    }

    /**
     * Verifies that a asText() returns "checked" or "unchecked" according to the state of the checkbox.
     * @throws Exception if the test fails
     */
    @Test
    public void asText() throws Exception {
        final String html
            = "<html><head></head><body>\n"
            + "<form id='form1'>\n"
            + "    <input type='checkbox' name='checkbox' id='checkbox'>Check me</input>\n"
            + "</form></body></html>";

        final HtmlPage page = loadPage(html);

        final HtmlCheckBoxInput checkBox = page.getHtmlElementById("checkbox");
        assertEquals("unchecked", checkBox.asText());
        assertEquals("uncheckedCheck me", page.asText());
        checkBox.setChecked(true);
        assertEquals("checked", checkBox.asText());
    }
}
