package com.example.ashis.propertysearch.data;

import android.provider.BaseColumns;


public class PropertyContract {

    public static final class PropertyEntry implements BaseColumns{

        public static final String TABLE_NAME = "property";
        public static final String COLUMN_PKT = "pkt";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_SECTOR = "sector";
        public static final String COLUMN_AREA="area";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_NOTES="notes";
        public static final String COLUMN_DEALER = "dealer";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_REMARKS = "remarks";
        public static final String COLUMN_DEALER_NAME = "dealerName";
        public static final String COLUMN_DATE = "postedDate";
        public static final String COLUMN_IS_IMPORTANT = "isImportant";
        public static final String COLUMN_COLOR = "color";
    }
}
