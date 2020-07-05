package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.domain.iteractor.GroupsIteractor
import ru.ifmo.statapp.presentation.presenter.GroupsCreatorPresenter
import ru.ifmo.statapp.presentation.view.GroupCreatorView
import javax.inject.Inject
import javax.inject.Provider

class GroupCreatorFragment : MvpAppCompatFragment(), GroupCreatorView {

    companion object {
        const val tag = "GroupsCreatorFragment"
    }

    @InjectPresenter
    lateinit var presenter: GroupsCreatorPresenter

    @Inject
    lateinit var presenterProvider: Provider<GroupsCreatorPresenter>

    @ProvidePresenter
    fun providePresenter() = presenterProvider.get()

    private lateinit var createGroupBtn: Button
    private lateinit var groupList: RecyclerView

    private lateinit var mainView: View

    private val adapter = GroupsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
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
    }

    override fun showGroups(groups: List<Group>) {
        adapter.groups = groups
        adapter.notifyDataSetChanged()
    }

    override fun showErrorMessage(messageRes: Int) {
        Toast.makeText(activity, messageRes, Toast.LENGTH_LONG).show()
    }

    inner class GroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

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