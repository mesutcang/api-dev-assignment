package com.assingment.service;

import com.assingment.models.CategorisedTransactions;
import com.assingment.models.UpdateCategory4Transaction;

/**
 * Created by mesutcang on 06/08/2019.
 */
public interface CategoryService {
    public CategorisedTransactions getCategorisedTransactions(String categoryId);

    public boolean updateCategory4Transaction(UpdateCategory4Transaction updateRequest);
}
