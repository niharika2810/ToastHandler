package com.toastfix.toasthandler_lint.registry

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.toastfix.toasthandler_lint.ToastHandlerDetector

class ToastHandlerIssueRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf(
            ToastHandlerDetector.ISSUE
        )

    override val api: Int = CURRENT_API

}
