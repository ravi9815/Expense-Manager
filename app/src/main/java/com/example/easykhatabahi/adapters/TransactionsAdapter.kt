package com.example.easykhatabahi.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easykhatabahi.R
import com.example.easykhatabahi.databinding.RowTransactionBinding
import com.example.easykhatabahi.models.Category
import com.example.easykhatabahi.models.Transaction
import com.example.easykhatabahi.utils.Constants
import com.example.easykhatabahi.utils.Helper
import com.example.easykhatabahi.MainActivity
import io.realm.RealmResults
import java.lang.String
import kotlin.Int

class TransactionsAdapter(var context: Context, transactions: RealmResults<Transaction>) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {
    var transactions: RealmResults<Transaction>

    init {
        this.transactions = transactions
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.row_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction: Transaction? = transactions[position]
        holder.binding.transactionAmount.setText(String.valueOf(transaction!!.amount))
        holder.binding.accountLbl.setText(transaction.account)
        holder.binding.transactionDate.setText(Helper.formatDate(transaction.date))
        holder.binding.transactionCategory.setText(transaction.category)
        val transactionCategory: Category? = Constants.getCategoryDetails(transaction.category!!)
        holder.binding.categoryIcon.setImageResource(transactionCategory!!.categoryImage)
        holder.binding.categoryIcon.setBackgroundTintList(
            context.getColorStateList(
                transactionCategory.categoryColor
            )
        )
        holder.binding.accountLbl.setBackgroundTintList(
            context.getColorStateList(
                Constants.getAccountsColor(
                    transaction.account
                )
            )
        )
        if (transaction!!.type.equals(Constants.INCOME)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor))
        } else if (transaction.type.equals(Constants.EXPENSE)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor))
        }
        holder.itemView.setOnLongClickListener {
            val deleteDialog = AlertDialog.Builder(context).create()
            deleteDialog.setTitle("Delete Transaction")
            deleteDialog.setMessage("Are you sure to delete this transaction?")
            deleteDialog.setButton(
                DialogInterface.BUTTON_POSITIVE, "Yes"
            ) { dialogInterface: DialogInterface?, i: Int ->
                (context as MainActivity).viewModel!!.deleteTransaction(
                    transaction
                )
            }
            deleteDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE, "No"
            ) { dialogInterface: DialogInterface?, i: Int -> deleteDialog.dismiss() }
            deleteDialog.show()
            false
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowTransactionBinding

        init {
            binding = RowTransactionBinding.bind(itemView)
        }
    }
}
