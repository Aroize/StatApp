package ru.ifmo.statapp.presentation.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.ifmo.statapp.domain.iteractor.GroupsIteractor
import ru.ifmo.statapp.presentation.fragment.GroupCreatorFragment
import ru.ifmo.statapp.presentation.view.GroupCreatorView
import javax.inject.Inject

class GroupsCreatorPresenter @Inject constructor(private val groupsIteractor: GroupsIteractor)
    : MvpPresenter<GroupCreatorView>() {

    private val disposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposable.add(groupsIteractor.groups()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { groups ->
                    Log.d(GroupCreatorFragment.tag, "UPD: New groups")
                    Log.d(GroupCreatorFragment.tag, "$groups")
                    viewState?.showGroups(groups)
                }, { e ->
                    Log.d(GroupCreatorFragment.tag, "Exception while requesting groups", e)
                    viewState?.showErrorMessage(android.R.string.httpErrorBadUrl)
                }, {
                    Log.d(GroupCreatorFragment.tag, "Remote are preprocessed")
                })
        )
    }

    override fun detachView(view: GroupCreatorView?) {
        super.detachView(view)
        disposable.clear()
    }
}