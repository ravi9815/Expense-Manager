package com.example.easykhatabahi.views.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easykhatabahi.MainActivity
import com.example.easykhatabahi.R
import com.example.easykhatabahi.adapters.AccountsAdapter
import com.example.easykhatabahi.adapters.AccountsAdapter.AccountsClickListener
import com.example.easykhatabahi.adapters.CategoryAdapter
import com.example.easykhatabahi.adapters.CategoryAdapter.CategoryClickListener
import com.example.easykhatabahi.databinding.FragmentAddTransactionBinding
import com.example.easykhatabahi.databinding.ListDialogBinding
import com.example.easykhatabahi.models.Account
import com.example.easykhatabahi.models.Category
import com.example.easykhatabahi.models.Transaction
import com.example.easykhatabahi.utils.Constants
import com.example.easykhatabahi.utils.Helper.formatDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTransactionFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentAddTransactionBinding
    var transaction: Transaction? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTransactionBinding.inflate(inflater)
        transaction = Transaction()

        binding.incomeBtn.setOnClickListener { view ->
            binding.incomeBtn.setBackground(requireContext().getDrawable(R.drawable.income_selector))
            binding.expenseBtn.setBackground(requireContext().getDrawable(R.drawable.default_selector))
            binding.expenseBtn.setTextColor(requireContext().getColor(R.color.textColor))
            binding.incomeBtn.setTextColor(requireContext().getColor(R.color.greenColor))
            transaction!!.type = Constants.INCOME
        }

        binding.expenseBtn.setOnClickListener { view ->
            binding.incomeBtn.setBackground(requireContext().getDrawable(R.drawable.default_selector))
            binding.expenseBtn.setBackground(requireContext().getDrawable(R.drawable.expense_selector))
            binding.incomeBtn.setTextColor(requireContext().getColor(R.color.textColor))
            binding.expenseBtn.setTextColor(requireContext().getColor(R.color.redColor))
            transaction!!.type = Constants.EXPENSE
        }

        binding.date.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
                calendar[Calendar.MONTH] = datePicker.month
                calendar[Calendar.YEAR] = datePicker.year

                val dateToShow =
                    formatDate(calendar.time)
                binding.date.setText(dateToShow)
                transaction!!.date = calendar.time
                transaction!!.id = calendar.time.time
            }
            datePickerDialog.show()
        })
        binding.category.setOnClickListener { c ->
            val dialogBinding: ListDialogBinding = ListDialogBinding.inflate(inflater)
            val categoryDialog =
                AlertDialog.Builder(context).create()
            categoryDialog.setView(dialogBinding.getRoot())
            val categoryAdapter = CategoryAdapter(
                requireContext(),
                Constants.categories!!,
                object : CategoryClickListener {
                    override fun onCategoryClicked(category: Category?) {
                        binding.category.setText(category!!.categoryName)
                        transaction!!.category = category.categoryName
                        categoryDialog.dismiss()
                    }
                })
            dialogBinding.recyclerView.setLayoutManager(GridLayoutManager(context, 3))
            dialogBinding.recyclerView.setAdapter(categoryAdapter)
            categoryDialog.show()
        }
        binding.account.setOnClickListener { c ->
            val dialogBinding: ListDialogBinding = ListDialogBinding.inflate(inflater)
            val accountsDialog =
                AlertDialog.Builder(context).create()
            accountsDialog.setView(dialogBinding.getRoot())
            val accounts =
                ArrayList<Account>()
            accounts.add(Account(0.0, "Cash"))
            accounts.add(Account(0.0, "Bank"))
            accounts.add(Account(0.0, "PayTM"))
            accounts.add(Account(0.0, "EasyPaisa"))
            accounts.add(Account(0.0, "Other"))

            val adapter =
                AccountsAdapter(requireContext(), accounts, object : AccountsClickListener {
                    override fun onAccountSelected(account: Account?) {
                        binding.account.setText(account!!.accountName)
                        transaction!!.account = account.accountName
                        accountsDialog.dismiss()
                    }
                })
            dialogBinding.recyclerView.setLayoutManager(LinearLayoutManager(context))
            dialogBinding.recyclerView.setAdapter(adapter)
            accountsDialog.show()
        }
        binding.saveTransactionBtn.setOnClickListener { c ->



                val amount: Double = binding.amount.getText().toString().toDouble()
                val note: String = binding.note.getText().toString()
                if (transaction!!.type == Constants.EXPENSE) {
                    transaction!!.amount = amount * -1
                } else {
                    transaction!!.amount = amount
                }
                transaction!!.note = note
                (activity as MainActivity?)!!.viewModel!!.addTransaction(transaction!!)
                (activity as MainActivity?)!!.transactions
                dismiss()

        }
        return binding.getRoot()
    }
}