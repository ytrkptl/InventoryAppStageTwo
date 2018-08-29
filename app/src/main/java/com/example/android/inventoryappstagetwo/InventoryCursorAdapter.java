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
package com.example.android.inventoryappstagetwo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryappstagetwo.data.InventoryContract.InventoryEntry;

/**
 * {@link InventoryCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of inventory data as its data source. This adapter knows
 * how to create list items for each row of inventory data in the {@link Cursor}.
 */
public class InventoryCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the inventory data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);

        // Find the columns of inventory attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);

        // Read the inventory attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        String productQuantity = cursor.getString(quantityColumnIndex);

        // If the product quantity is empty string or null, then use some default text
        // that says "Sold Out", so the TextView isn't blank.
        if (TextUtils.isEmpty(productQuantity)) {
            productQuantity = context.getString(R.string.sold_out);
        }

        // Update the TextViews with the attributes for the current inventory product
        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        quantityTextView.setText(productQuantity);

        Button soldButton = view.findViewById(R.id.sold_button);
        final String finalProductQuantity = productQuantity;
        soldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the following five lines were shared by Vladimir I., one of our classmates
                // https://github.com/Boubari97/InventoryApp
                View parent = (View) v.getParent();
                ListView lv = (ListView) parent.getParent();
                int position = lv.getPositionForView(parent);
                long id = lv.getItemIdAtPosition(position);
                int quantity = Integer.parseInt(finalProductQuantity) - 1;

                // Used Sreekant S. code from the following link. He is also one of our classmates
                // https://github.com/geekykant/InventoryApp_2/blob/master/app/src/main/java/com/diyandroid/inventory/ProductCursorAdapter.java
                Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                ContentValues values = new ContentValues();
                if (quantity < 0) {
                    Toast.makeText(v.getContext(), R.string.sold_out, Toast.LENGTH_SHORT).show();
                } else {
                    values.put(InventoryEntry.COLUMN_QUANTITY, quantity);

                    int rowsAffected = v.getContext().getContentResolver().update(currentProductUri, values, null, null);

                    // Show a toast message depending on whether or not the update was successful.
                    if (rowsAffected == 0) {
                        // If no rows were affected, then there was an error with the update.
                        Toast.makeText(v.getContext(), "updating failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}