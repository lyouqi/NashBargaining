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

import java.util.Map;

import com.gargoylesoftware.htmlunit.SgmlPage;

/**
 * An element that is returned for an HTML tag that is not supported by this framework.
 *
 * @version $Revision: 10218 $
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author David K. Taylor
 * @author <a href="mailto:cse@dynabean.de">Christian Sell</a>
 * @author Ahmed Ashour
 * @author Rodney Gitzel
 * @author Ronald Brill
 * @author Frank Danek
 */
public class HtmlUnknownElement extends HtmlElement {

    private boolean createdByJavascript_;

    /**
     * Creates an instance.
     *
     * @param page the page that contains this element
     * @param tagName the HTML tag represented by this object
     * @param attributes the initial attributes
     */
    HtmlUnknownElement(final SgmlPage page, final String tagName, final Map<String, DomAttr> attributes) {
        super(tagName, page, attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isTrimmedText() {
        return false;
    }

    /**
     * <span style="color:red">INTERNAL API - SUBJECT TO CHANGE AT ANY TIME - USE AT YOUR OWN RISK.</span><br/>
     *
     * Marks this frame as created by javascript. This is needed to handle
     * some special IE behavior.
     */
    public void markAsCreatedByJavascript() {
        createdByJavascript_ = true;
    }

    /**
     * <span style="color:red">INTERNAL API - SUBJECT TO CHANGE AT ANY TIME - USE AT YOUR OWN RISK.</span><br/>
     *
     * Returns true if this frame was created by javascript. This is needed to handle
     * some special IE behavior.
     * @return true or false
     */
    public boolean wasCreatedByJavascript() {
        return createdByJavascript_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DisplayStyle getDefaultStyleDisplay() {
        switch (getTagName()) {
            case HtmlRp.TAG_NAME:
            case HtmlRt.TAG_NAME:
                if (wasCreatedByJavascript() && getParentNode() == null) {
                    return DisplayStyle.BLOCK;
                }
                break;

            default:
        }
        return DisplayStyle.INLINE;
    }
}
