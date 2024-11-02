package com.example.easykhatabahi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easykhatabahi.R
import com.example.easykhatabahi.databinding.RowAccountBinding
import com.example.easykhatabahi.models.Account

class AccountsAdapter(
    var context: Context,
    accountArrayList: ArrayList<Account>,
    accountsClickListener: AccountsClickListener
) :
    RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {
    var accountArrayList: ArrayList<Account>

    interface AccountsClickListener {
        fun onAccountSelected(account: Account?)
    }

    var accountsClickListener: AccountsClickListener

    init {
        this.accountArrayList = accountArrayList
        this.accountsClickListener = accountsClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        return AccountsViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.row_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account: Account = accountArrayList[position]
        holder.binding.accountName.setText(account.accountName)
        holder.itemView.setOnClickListener { c: View? ->
            accountsClickListener.onAccountSelected(
                account
            )
        }
    }

    override fun getItemCount(): Int {
        return accountArrayList.size
    }

    inner class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowAccountBinding

        init {
            binding = RowAccountBinding.bind(itemView)
        }
    }
}
