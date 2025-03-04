package tests;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.checkerframework.framework.test.TestConfigurationBuilder.buildDefaultConfiguration;
import org.checkerframework.com.google.common.collect.ImmutableList;
import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.checkerframework.framework.test.TestConfiguration;
import org.checkerframework.framework.test.TestUtilities;
import org.checkerframework.framework.test.TypecheckExecutor;
import org.checkerframework.framework.test.TypecheckResult;
import org.junit.runners.Parameterized.Parameters;

public class AutoValueTest extends CheckerFrameworkPerDirectoryTest {

	  private static final ImmutableList<String> ANNOTATION_PROCS =
	      ImmutableList.of(
	          "com.google.auto.value.extension.memoized.processor.MemoizedValidator",
	          "com.google.auto.value.processor.AutoAnnotationProcessor",
	          "com.google.auto.value.processor.AutoOneOfProcessor",
	          "com.google.auto.value.processor.AutoValueBuilderProcessor",
	          "com.google.auto.value.processor.AutoValueProcessor",
	          org.checkerframework.checker.returnsrcvr.ReturnsRcvrChecker.class.getName());

	  public AutoValueTest(List<File> testFiles) {
	    super(
	        testFiles,
	        org.checkerframework.checker.returnsrcvr.ReturnsRcvrChecker.class,
	        "basic",
	        "-Anomsgtext",
	        "-nowarn");
	  }

	  @Parameters
	  public static String[] getTestDirs() {
	    return new String[] {"autovalue"};
	  }

	  /**
	   * copy-pasted code from {@link CheckerFrameworkPerDirectoryTest#run()}, except that we change the
	   * annotation processors to {@link #ANNOTATION_PROCS}
	   */
	  @Override
	  public void run() {
	    boolean shouldEmitDebugInfo = TestUtilities.getShouldEmitDebugInfo();
	    List<String> customizedOptions = customizeOptions(Collections.unmodifiableList(checkerOptions));
	    TestConfiguration config =
	        buildDefaultConfiguration(
	            testDir, testFiles, ANNOTATION_PROCS, customizedOptions, shouldEmitDebugInfo);
	    TypecheckResult testResult = new TypecheckExecutor().runTest(config);
	    TestUtilities.assertResultsAreValid(testResult);
	  }
	}