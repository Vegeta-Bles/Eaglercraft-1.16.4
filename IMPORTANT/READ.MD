# INSTRUCTIONS FOR AI

These are the instructions for AI models working on this codebase.

---

## ⚠️ READ THIS FILE BEFORE EDITING ANY CODE ⚠️

**ALWAYS READ THIS ENTIRE FILE BEFORE STARTING ANY TASK OR EDITING ANY CODE!**

- **This file may have been updated** - instructions change, requirements evolve, new critical information is added
- **Don't assume you know what's in here** - even if you've read it before, read it again
- **Check for updates** - requirements, build processes, and critical warnings may have changed
- **This file contains critical information** that will save you time and prevent mistakes

**If you start editing code without reading this file first, you're likely to miss critical requirements and your work may be incomplete or incorrect!**

---

## CRITICAL REQUIREMENTS

### Build and Validation
- **MANDATORY**: Before reporting completion to the user, you MUST build/run the code. There is no other acceptable way to finish. Any other methods are not permitted.
- **⚠️ CRITICAL: USE ERROR-ONLY SCRIPT OR TASK IS NOT COMPLETE ⚠️**
  - **YOU MUST USE `./build-errors-only.sh [task]` (or `build-errors-only.bat` on Windows) for ALL build/validation commands!**
  - **IF YOU RUN `./gradlew` DIRECTLY WITHOUT THE ERROR-ONLY SCRIPT, THE TASK IS NOT COMPLETED!**
  - **THE TASK WILL BE CONSIDERED INCOMPLETE AND UNACCEPTABLE IF YOU SKIP THIS STEP!**
  - This script filters output to show only errors and warnings, preventing context overflow and ensuring you see what matters.
  - Examples:
    - ✅ CORRECT: `./build-errors-only.sh build`
    - ✅ CORRECT: `./build-errors-only.sh teavmJs`
    - ✅ CORRECT: `./build-errors-only.sh compileJava`
    - ❌ WRONG: `./gradlew build` (TASK INCOMPLETE! DON'T DO THIS!)
    - ❌ WRONG: `./gradlew compileJava` (TASK INCOMPLETE! DON'T DO THIS!)
- If the error-only script doesn't show enough detail, you may run the full build AFTER using the error-only script, but the error-only script must be used first.
- Before asking the user to run/build, check for obvious issues like syntax errors, unresolved imports, or typos.

### Documentation and Tracking
- In every subdirectory where you make changes, create or update an `AGENTS.MD` file so future agents know what was happening in the code.
- Document all changes you make in `AGENTS.MD` files with dates and reasons, including the decision rationale.
- If removing or renaming code, note the original names/locations clearly in `AGENTS.MD`.
- If you find inconsistency or uncertainty in requirements, record assumptions made and why.

## THOROUGHNESS REQUIREMENTS

**BE THOROUGH. DO NOT SKIP STEPS. COMPLETE THE ENTIRE TASK.**

### General Thoroughness
- **Read ALL relevant files** before making changes. Don't assume - verify.
- **Check ALL error messages** - don't just fix the first one you see. There may be related errors.
- **Verify your changes work** - don't just make the change and move on. Test it.
- **Follow the complete workflow** - don't skip validation steps, don't skip documentation, don't skip testing.
- **Check edge cases** - think about what could go wrong, what inputs are invalid, what happens in error conditions.
- **Review your work** - before marking complete, review what you changed and verify it's correct.

### Error Fixing Thoroughness
- **Fix ALL errors in the target scope** - if asked to fix errors in a directory, fix ALL of them, not just some.
- **Check for related errors** - fixing one error may reveal others. Check for cascading issues.
- **Verify fixes actually work** - don't just remove the error message, make sure the code actually compiles/runs.
- **Check for warnings too** - while warnings are lower priority, address them if they're easy fixes.
- **Document what you couldn't fix** - if something can't be fixed, explain why clearly in error logs.

### Build Validation Thoroughness
- **ALWAYS run the build** - never skip this step, even if you think your changes are simple.
- **Use the error-only script** - this is mandatory, not optional (see Build and Validation section).
- **Check the ENTIRE output** - don't just look at the first few lines. Scroll through all errors.
- **Verify no new errors introduced** - your changes shouldn't break existing functionality.
- **Test the actual functionality** - if possible, verify the code actually works, not just compiles.

### Documentation Thoroughness
- **Document ALL changes** - every file you modify should be documented in AGENTS.MD.
- **Explain WHY** - don't just say what you changed, explain why you changed it.
- **Include context** - future agents need to understand the situation, not just the change.
- **Update relevant docs** - if you change how something works, update the documentation.

### Code Review Thoroughness
- **Check for similar patterns** - if you fix one instance, check for others that need the same fix.
- **Verify consistency** - make sure your changes match the codebase style and patterns.
- **Check dependencies** - if you add new code, verify all dependencies are available.
- **Test edge cases** - think about boundary conditions, null values, empty collections, etc.

**Remember: Incomplete work is worse than no work. If you can't complete the task thoroughly, document what's incomplete and why.**

## CODE QUALITY STANDARDS

### Readability and Maintainability
- Always make the code easy to understand and reliable.
- Use clear, descriptive variable names unless matching an existing codebase pattern.
- Always prefer readability and maintainability over cleverness or premature optimization.
- Every function you write should have a brief comment explaining its purpose and expected input/output.

### Code Organization
- When creating new files, follow existing naming conventions and directory structure if possible.
- Keep the workspace neat and orderly. Remove any files that you do not need periodically.
- If making significant architectural changes, propose and document a migration/rollback plan.

## TESTING REQUIREMENTS

- Add tests for every section of the code. This way if something breaks unexpectedly, you know exactly what went wrong.
- When adding tests, prefer small, targeted tests and put them in test or equivalent folders. Explain what is covered in a TESTS.MD file.

## ERROR PRIORITIZATION

When fixing errors, prioritize in this order:
1. **Compilation errors** - These prevent the code from building. Fix these first!
2. **Runtime errors** - Code compiles but crashes. Fix after compilation errors.
3. **Unused variable/field warnings** - Easy fixes, do these when convenient.
4. **Type safety warnings** - Often acceptable in Minecraft codebase, but fix if easy.
5. **Deprecation warnings** - Lowest priority, but note them for future updates.

**Remember**: Fix ALL errors in the target scope. Warnings are lower priority but should be addressed if they're quick fixes.

## WHEN YOU CAN'T FIX SOMETHING

If you encounter errors you cannot fix:
1. **Document it clearly** - Create or update error logs in `FIXERRORS/errorLog-{date}.md`
2. **Explain why** - What did you try? Why didn't it work? What's blocking you?
3. **Provide context** - What file? What line? What's the error message?
4. **Suggest next steps** - What would need to happen to fix it? (e.g., "Requires external dependency X")
5. **Don't leave it broken** - If you can't fix it, at least document it thoroughly so someone else can

**Don't just skip errors** - either fix them or document why you couldn't!

## ASKING FOR HELP VS. MAKING ASSUMPTIONS

**Ask the user when:**
- Instructions are unclear or conflicting
- You need to make a significant architectural decision
- You're not sure which approach to take
- The task scope is ambiguous

**Make reasonable assumptions when:**
- The fix is obvious and low-risk
- You can document your assumption clearly
- The alternative is to ask about every small detail
- You're following established patterns in the codebase

**Always document assumptions** in AGENTS.MD so future agents understand your reasoning.

## HANDLING BUILD FAILURES

If the build fails:
1. **Read the error messages carefully** - Don't just see "FAILURE" and give up!
2. **Check if it's a dependency issue** - Missing libraries? Wrong versions?
3. **Check if it's a TeaVM compatibility issue** - See `TEAVM_CONSTRAINTS.MD`
4. **Check if your changes broke something** - Did you introduce new errors?
5. **Document the failure** - What failed? Why? What did you try?
6. **Don't mark task complete** - If the build fails, the task is incomplete!

**If the build fails due to missing dependencies** (like external libraries), document this clearly - it's not a code issue, it's a dependency resolution issue.

## VERIFYING YOUR WORK

After making changes:
1. **Build the code** - Use `./build-errors-only.sh compileJava` (or appropriate task)
2. **Check for new errors** - Your changes shouldn't break existing functionality
3. **Verify the fix works** - Don't just remove the error message, make sure it actually works
4. **Test edge cases** - What happens with null? Empty strings? Invalid input?
5. **Check related code** - Did your change affect anything else?

**Don't assume your fix works** - verify it!

## CONFLICT RESOLUTION

- If instructions conflict, ask the user for clarification before proceeding.
- If you're unsure about requirements, document your assumptions and proceed, but note the uncertainty.

## VERSION CONTROL

- Regularly backup your work or instruct user to use version control to avoid data loss.

## ADDITIONAL CRITICAL DOCUMENTATION

**MUST READ** before making significant changes:
- `TEAVM_CONSTRAINTS.MD` - TeaVM limitations and what code patterns to avoid
- `BUILD_PROCESS.MD` - How the build works and common build issues
- `MINECRAFT_1.16.4.MD` - Version-specific requirements and compatibility
- `COMMON_PITFALLS.MD` - Most common mistakes that break things

These files contain critical information that will prevent hours of debugging.

---

## FINAL REMINDER

**⚠️ BEFORE MARKING ANY TASK AS COMPLETE ⚠️**

1. **Did you use `./build-errors-only.sh` for ALL build/validation commands?**
   - If NO → **TASK IS INCOMPLETE. DO NOT MARK AS DONE.**
   - If YES → Continue to next check.

2. **Did you run the build and verify it succeeds (or document why it doesn't)?**
   - If NO → **TASK IS INCOMPLETE. DO NOT MARK AS DONE.**
   - If YES → Continue to next check.

3. **Did you document all changes in AGENTS.MD files?**
   - If NO → **TASK IS INCOMPLETE. DO NOT MARK AS DONE.**
   - If YES → Continue to next check.

4. **Did you fix ALL errors in the target scope (or document why you couldn't)?**
   - If NO → **TASK IS INCOMPLETE. DO NOT MARK AS DONE.**
   - If YES → Continue to next check.

5. **Did you verify your changes actually work (not just compile)?**
   - If NO → **TASK IS INCOMPLETE. DO NOT MARK AS DONE.**
   - If YES → Task may be complete.

6. **If you couldn't fix something, did you document it clearly in error logs?**
   - If NO → **TASK IS INCOMPLETE. DO NOT MARK AS DONE.**
   - If YES → Task may be complete.

**Remember: Using `./gradlew` directly instead of `./build-errors-only.sh` means the task is NOT completed, regardless of what else you did. This is non-negotiable.**
