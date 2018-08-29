/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.inventoryappstagetwo.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * API Contract for the Inventory app.
 */
public final class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryappstagetwo";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.inventoryappstagetwo/inventoryappstagetwo/ is a valid path for
     * looking at inventory data. content://com.example.android.inventoryappstagetwo/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_INVENTORY = "inventoryappstagetwo";

    /**
     * Inner class that defines constant values for the inventory database table.
     * Each entry in the table represents a single inventory.
     */
    public static final class InventoryEntry implements BaseColumns {

        /** The content URI to access the inventory data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of inventory.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single inventory.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /** Name of database table for inventory */
        public final static String TABLE_NAME = "inventoryappstagetwo";

        /**
         * Unique ID number for the inventory (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the inventory product.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME ="name";

        /**
         * Price of the inventory product.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRICE = "price";

        /**
         * Quantity of the inventory product.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_QUANTITY = "quantity";

        /**
         * Name of the supplier
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplier";

        /**
         * Phone number of the supplier
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_PH_NO = "phone";

        /**
         * Possible values for the quantity of the product when no quantity is entered.
         */
        public static final int QUANTITY_ZERO = 0;
    }

}