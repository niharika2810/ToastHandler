package com.toastfix.toasthandler_lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class ToastHandlerDetectorTest {

    @Test
    fun `Toast usage in Java`() {

        lint()
            .files(
                java(
                    """
                        import android.widget.Toast;
                        import android.content.Context;
                        
                        public class SomeJavaClass {
                        
                            private void function(Context context) {
                                Toast.makeText(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show();
                            }
                            
                        }
                    """
                ).indented()
            )
            .issues(ToastHandlerDetector.ISSUE)
            .run()
            .expect(
                """
                    src/SomeJavaClass.java:7: Warning: Usage of android Toast is prohibited [ToastUsageWarning]
                            Toast.makeText(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show();
                            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    0 errors, 1 warnings
                """
            )
            .expectFixDiffs(
                """
                    Fix for src/SomeJavaClass.java line 7: Use ToastHandler.getToastInstance():
                    @@ -7 +7
                    -         Toast.makeText(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show();
                    +         com.toastfix.toastcompatwrapper.ToastHandler.getToastInstance(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show();
                """
            )

    }

    @Test
    fun `Toast usage in Kotlin`() {

        lint()
            .files(
                kotlin(
                    """
                        import android.widget.Toast
                        import android.content.Context
                        
                        fun function(context: Context) {
                            Toast.makeText(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show()
                        }
                    """
                ).indented()
            )
            .issues(ToastHandlerDetector.ISSUE)
            .run()
            .expect(
                """
                    src/test.kt:5: Warning: Usage of android Toast is prohibited [ToastUsageWarning]
                        Toast.makeText(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show()
                        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    0 errors, 1 warnings
                """
            )
            .expectFixDiffs(
                """
                    Fix for src/test.kt line 5: Use ToastHandler.getToastInstance():
                    @@ -5 +5
                    -     Toast.makeText(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show()
                    +     com.toastfix.toastcompatwrapper.ToastHandler.getToastInstance(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show()
                """
            )

    }

    @Test
    fun `ToastHandler usage in Java`() {

        lint()
            .files(
                java(
                    """
                        import com.toastfix.toastcompatwrapper.ToastHandler;
                        import android.content.Context;
                        
                        public class SomeJavaClass {
                        
                            private void function(Context context) {
                                ToastHandler.getToastInstance(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show();
                            }
                            
                        }
                    """
                ).indented()
            )
            .issues(ToastHandlerDetector.ISSUE)
            .run()
            .expectClean()

    }

    @Test
    fun `ToastHandler usage in Kotlin`() {

        lint()
            .files(
                kotlin(
                    """
                        import com.toastfix.toastcompatwrapper.ToastHandler
                        import android.content.Context
                        
                        fun function(context: Context) {
                            ToastHandler.getToastInstance(context, "Hi, I am Toast", Toast.LENGTH_SHORT).show()
                        }
                    """
                ).indented()
            )
            .issues(ToastHandlerDetector.ISSUE)
            .run()
            .expectClean()

    }

}