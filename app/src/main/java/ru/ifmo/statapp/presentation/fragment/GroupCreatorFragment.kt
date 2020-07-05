package ru.ifmo.statapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.ifmo.statapp.App
import ru.ifmo.statapp.R
import ru.ifmo.statapp.data.db.entity.Group
import ru.ifmo.statapp.domain.iteractor.GroupsIteractor
import javax.inject.Inject

class GroupCreatorFragment : Fragment() {

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var groupsIteractor: GroupsIteractor

    private lateinit var createGroupBtn: Button
    private lateinit var groupList: RecyclerView

    private lateinit var mainView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(groupsIteractor.groups()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { groups ->
                    Log.d(GroupCreatorFragment.tag, "UPD: New groups")
                    Log.d(GroupCreatorFragment.tag, "$groups")
                }, { e ->
                    Log.d(GroupCreatorFragment.tag, "Exception while requesting groups", e)
                }, {
                    Log.d(GroupCreatorFragment.tag, "Remote are preprocessed")
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun initViews() {
        createGroupBtn = mainView.findViewById(R.id.create_group_btn)
        groupList = mainView.findViewById(R.id.group_recycler)

    }

    companion object {
        const val tag = "GroupsCreatorFragment"
    }

    inner class GroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(group: Group) {
            TODO()
        }
    }

    inner class GroupsAdapter() : RecyclerView.Adapter<GroupsViewHolder>() {
        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
            TODO("Not yet implemented")
        }
    }
}