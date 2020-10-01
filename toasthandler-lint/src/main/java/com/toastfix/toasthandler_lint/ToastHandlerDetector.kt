package com.toastfix.toasthandler_lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class ToastHandlerDetector : Detector(), SourceCodeScanner {

    override fun getApplicableMethodNames(): List<String> {
        return listOf("makeText")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.widget.Toast")) {
            reportUsage(context, node, method)
        }
    }

    private fun reportUsage(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getCallLocation(
                call = node,
                includeReceiver = true,
                includeArguments = true
            ),
            message = "Usage of android Toast is prohibited",
            quickfixData = fix()
                .name("Use ToastHandler.getToastInstance()")
                .replace()
                .text("Toast.makeText")
                .with("com.toastfix.toastcompatwrapper.ToastHandler.getToastInstance")
                .shortenNames()
                .reformat(true)
                .build()
        )
    }

    companion object {
        val ISSUE = Issue.create(
            id = "ToastUsageWarning",
            briefDescription = "The android Toast should not be used",
            explanation = "The android Toast should not be used, use ToastHandler instead to avoid BadTokenException on Android API level 25",
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(ToastHandlerDetector::class.java, Scope.JAVA_FILE_SCOPE)
        ).setAndroidSpecific(true)
    }

}
