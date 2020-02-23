package com.techneapps.notestaking;

import android.text.TextUtils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ValidationTest {
    @Test
    public void titleValidator_EmptyString_ReturnsFalse() {
        assertFalse(TextUtils.isEmpty(("")));
    }

    @Test
    public void contentValidator_EmptyString_ReturnsFalse() {
        assertFalse(TextUtils.isEmpty(("")));
    }

}
