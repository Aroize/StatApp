package ru.ifmo.statapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.domain.MainStateAcceptor
import ru.ifmo.statapp.presentation.activity.MainActivity
import ru.ifmo.statapp.presentation.presenter.GroupsCreatorPresenter
import ru.ifmo.statapp.presentation.view.GroupCreatorView
import javax.inject.Inject
import javax.inject.Provider

class GroupCreatorFragment : MvpAppCompatFragment(), GroupCreatorView, View.OnClickListener {

    companion object {
        const val tag = "GroupsCreatorFragment"
    }

    @InjectPresenter
    lateinit var presenter: GroupsCreatorPresenter

    @Inject
    lateinit var presenterProvider: Provider<GroupsCreatorPresenter>

    @ProvidePresenter
    fun providePresenter(): GroupsCreatorPresenter = presenterProvider.get()

    private lateinit var createGroupBtn: Button
    private lateinit var groupList: RecyclerView

    private lateinit var mainView: View

    private val adapter = GroupsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        presenter.getGroups()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_group_creator, container, false)
        initViews()
        return mainView
    }

    private fun initViews() {
        createGroupBtn = mainView.findViewById(R.id.create_group_btn)
        groupList = mainView.findViewById(R.id.group_recycler)
        groupList.adapter = adapter
        createGroupBtn.setOnClickListener(this)
    }

    private fun openGroup(group: Group) {
        (activity as MainActivity).showGroup(group)
    }

    override fun showGroups(groups: List<Group>) {
        adapter.groups = groups
        adapter.notifyDataSetChanged()
    }

    override fun showErrorMessage(messageRes: Int) {
        Toast.makeText(activity, messageRes, Toast.LENGTH_LONG).show()
    }

    override fun showErrorMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun addGroup(group: Group) {
        adapter.groups += group
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val groupName = EditText(activity)
        AlertDialog.Builder(activity as Context)
            .setView(groupName)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                presenter.createGroup(groupName.text.toString())
                dialog.dismiss()
            }
            .show()
    }

    inner class GroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener { openGroup(group) }
        }

        private val groupName = itemView.findViewById<TextView>(R.id.group_name)

        private var group = Group()

        fun bind(group: Group) {
            this.group = group
            groupName.text = group.name
        }
    }

    inner class GroupsAdapter : RecyclerView.Adapter<GroupsViewHolder>() {

        var groups: List<Group> = emptyList()

        override fun getItemCount() = groups.size

        override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
            holder.bind(groups[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
            val itemView = layoutInflater.inflate(R.layout.holder_group, parent, false)
            return GroupsViewHolder(itemView)
        }
    }
}