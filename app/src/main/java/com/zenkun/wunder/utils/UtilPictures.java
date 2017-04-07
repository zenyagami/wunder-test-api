package com.zenkun.wunder.utils;

import java.util.Random;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class UtilPictures {
   private static final String[] carsUrlList= new String[]{"http://estaticos.marca.com/imagenes/2015/09/07/motor/modelos-coches/1441625279_extras_noticia_foton_7_1.jpg",
           "http://i2.cdn.cnn.com/cnnnext/dam/assets/160120103725-toyota-ft-1-concept-exterior-super-169.jpg"
            ,"http://www.vw.com.mx/content/medialib/vwd4/mx_mexico_/modelos/modelos-2016/gti/home/_jcr_content/renditions/rendition.file/home-golf.jpg"
            ,"http://www.bmw.com.mx/content/dam/bmw/common/all-models/2-series/coupe/2013/at-a-glance/intro-header-fullwidth_01.jpg/jcr:content/renditions/cq5dam.resized.img.485.low.time1447942345037.jpg"
            , "https://www.barbagallo.com.au/images/uploads/model_profiles/lamborghini-huracan-spyder-model-profile.jpg"
            , "http://www.autobild.es/sites/default/files/imagecache/ab_galeria_coches/externals/laferrari.jpg"};
    public static String getRandomColor()
    {
        int rnd =new Random().nextInt(carsUrlList.length);
        return carsUrlList[rnd];
    }

}
