package com.zenkun.wunder;

import com.google.android.gms.maps.model.LatLng;
import com.zenkun.wunder.utils.UtilLocation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Zen zenyagami@gmail.com on 08/04/2017.
 */

@RunWith(Parameterized.class)
public class UtilLocationTest {

    private String latLng;

    @Parameterized.Parameters
    public static Collection<String> testParams(){
        String[] list= new String[]{
                "10.07526,53.59301,"
                ,"9.99622,53.54847"
                ,"9.97417,53.61274"
                ,"10.07838,53.56388"

        };

        return Arrays.asList(list);
    }
    public UtilLocationTest(String latLng)
    {
        this.latLng=latLng;
    }
    @Test
    public void isValidLatLng()
    {
        Assert.assertTrue(UtilLocation.toLatLng(latLng) != null);

    }



}
