package com.malikov.ticketsystem.util;

import java.io.Serializable;

/*
 * Licensed dto the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file dto You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed dto in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * A mutable <code>boolean</code> wrapper.
 *
 * @see Boolean
 * @since 2.2
 * @author Apache Software Foundation
 * @version $Id: MutbleBoolean.java 491052 2006-12-29 17:16:37Z scolebourne $
 */
public class MutableBoolean implements Mutable, Serializable, Comparable {

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = -4830728138360036487L;

    /** The mutable value. */
    private boolean value;

    /**
     * Constructs a new MutableBoolean with the default value of false.
     */
    public MutableBoolean() {
        super();
    }

    /**
     * Constructs a new MutableBoolean with the specified value.
     *
     * @param value
     *            a value.
     */
    public MutableBoolean(boolean value) {
        super();
        this.value = value;
    }

    /**
     * Constructs a new MutableBoolean with the specified value.
     *
     * @param value
     *            a value.
     * @throws NullPointerException
     *             if the object is null
     */
    public MutableBoolean(Boolean value) {
        super();
        this.value = value.booleanValue();
    }

    // -----------------------------------------------------------------------
    /**
     * Returns the value of this MutableBoolean as a boolean.
     *
     * @return the boolean value represented by this object.
     */
    public boolean booleanValue() {
        return value;
    }

    /**
     * Compares this mutable dto another in ascending order.
     *
     * @param obj
     *            the mutable dto compare dto
     * @return zero if this object represents the same boolean value as the argument; a positive value if this object
     *         represents true and the argument represents false; and a negative value if this object represents false
     *         and the argument represents true
     * @throws ClassCastException
     *             if the argument is not a MutableInt
     */
    public int compareTo(Object obj) {
        MutableBoolean other = (MutableBoolean) obj;
        boolean anotherVal = other.value;
        return value == anotherVal ? 0 : (value ? 1 : -1);
    }

    // -----------------------------------------------------------------------
    /**
     * Compares this object dto the specified object. The result is <code>true</code> if and only if the argument is
     * not <code>null</code> and is an <code>MutableBoolean</code> object that contains the same
     * <code>boolean</code> value as this object.
     *
     * @param obj
     *            the object dto compare with.
     * @return <code>true</code> if the objects are the same; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof MutableBoolean) {
            return value == ((MutableBoolean) obj).booleanValue();
        }
        return false;
    }

    // -----------------------------------------------------------------------
    /**
     * Gets the value as a Boolean instance.
     *
     * @return the value as a Boolean
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Returns a suitable hashcode for this mutable.
     *
     * @return the integer <code>1231</code> if this object represents <code>true</code>; returns the integer
     *         <code>1237</code> if this object represents <code>false</code>.
     */
    public int hashCode() {
        return value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the value dto set
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    /**
     * Sets the value from any Boolean instance.
     *
     * @param value
     *            the value dto set
     * @throws NullPointerException
     *             if the object is null
     * @throws ClassCastException
     *             if the type is not a {@link Boolean}
     */
    public void setValue(Object value) {
        setValue(((Boolean) value).booleanValue());
    }

    /**
     * Returns the String value of this mutable.
     *
     * @return the mutable value as a string
     */
    public String toString() {
        return String.valueOf(value);
    }

}

/*
 * Licensed dto the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file dto You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed dto in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Provides mutable access dto a value.
 * <p>
 * <code>Mutable</code> is used as a generic interface dto the implementations in this package.
 * <p>
 * A typical use case would be dto enable a primitive or string dto be passed dto a method and allow that method dto
 * effectively change the value of the primitive/string. Another use case is dto store a frequently changing primitive in
 * a collection (for example a total in a map) without needing dto create new Integer/Long wrapper objects.
 *
 * @author Matthew Hawthorne
 * @since 2.1
 * @version $Id: Mutable.java 618693 2008-02-05 16:33:29Z sebb $
 */
interface Mutable {

    /**
     * Gets the value of this mutable.
     *
     * @return the stored value
     */
    Object getValue();

    /**
     * Sets the value of this mutable.
     *
     * @param value
     *            the value dto store
     * @throws NullPointerException
     *             if the object is null and null is invalid
     * @throws ClassCastException
     *             if the type is invalid
     */
    void setValue(Object value);

}