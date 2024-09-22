# Windows Startup Application Script

This guide helps you create a batch script to run your Java application automatically at Windows startup using Task Scheduler.

## Step 1: Create a Batch Script to Run the Application

1. Open a text editor (like Notepad) and create a new file.
2. Add the following content to the file:

    ```batch
    @echo off
    cd C:\path\to\your\jar\directory
    java -jar your-application.jar
    ```

    - Replace `C:\path\to\your\jar\directory` with the actual path where your `.jar` file is located.
    - Replace `your-application.jar` with the name of your actual `.jar` file.

3. Save the file as `startMyApp.bat` in a folder, e.g., `C:\Scripts`.

## Step 2: Set Up a Scheduled Task to Run the Batch Script at Startup

1. Press `Win + R`, type `taskschd.msc`, and press Enter to open **Task Scheduler**.
2. Click **Create Task** on the right-hand side.
3. In the **General** tab:
    - Enter a name for your task, e.g., **Start MyApp**.
4. In the **Triggers** tab:
    - Click **New**.
    - In the **Begin the task** dropdown, select **At startup**.
5. In the **Actions** tab:
    - Click **New**.
    - In the **Program/script** field, browse to and select your batch file (`C:\Scripts\startMyApp.bat`).
6. (Optional) In the **Conditions** tab:
    - Uncheck **Start the task only if the computer is on AC power** if necessary.
7. In the **Settings** tab:
    - Check **Allow task to be run on demand**.
    - Check **If the task fails, restart every** and configure the retry settings if desired.

8. Click **OK** to save the task.

## Step 3: Reboot to Test

1. Restart your computer.
2. The application should automatically run after startup.

## Additional Considerations

- **Run as a specific user:** Ensure the application runs as the user with the correct permissions to access required files and resources.
- **Logging:** Ensure that your application writes logs somewhere persistent for debugging. You can redirect output to log files or set up specific logging configurations in your application.
- **Auto-restart on failure:** Consider using the **Restart every** setting in Task Scheduler to automatically restart the task if it fails.
# schoolSoftwareBackend
