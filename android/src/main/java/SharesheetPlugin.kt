// Copyright 2019-2023 Tauri Programme within The Commons Conservancy
// SPDX-License-Identifier: Apache-2.0
// SPDX-License-Identifier: MIT

package app.tauri.sharesheet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Invoke
import app.tauri.plugin.Plugin
import java.io.File
import java.io.IOException
import androidx.core.content.FileProvider

@InvokeArg
class ShareTextOptions {
    lateinit var text: String
    var mimeType: String = "text/plain"
    var title: String? = null
}

@InvokeArg
class ShareFileOptions {
    lateinit var file: String
    var mimeType: String = "text/plain"
    var title: String? = null
}

@TauriPlugin
class SharesheetPlugin(private val activity: Activity): Plugin(activity) {
    private val shareCacheFolder: File
        get() = File(activity.cacheDir, "shared")

    private val providerAuthority: String by lazy {
        activity.packageName + ".sharesheet.fileprovider"
    }

    @Throws(IOException::class)
    private fun copyToShareCacheFolder(file: File): Uri {
        val folder = shareCacheFolder
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val newFile = File(folder, file.name)
        file.copyTo(newFile, true)
        return FileProvider.getUriForFile(activity, providerAuthority, newFile)
    }

    /**
     * Open the Sharesheet to share some text
     */
    @Command
    fun shareText(invoke: Invoke) {
        val args = invoke.parseArgs(ShareTextOptions::class.java)

        val sendIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.type = args.mimeType
            this.putExtra(Intent.EXTRA_TEXT, args.text)
            this.putExtra(Intent.EXTRA_TITLE, args.title)
        }

        val shareIntent = Intent.createChooser(sendIntent, null);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.applicationContext?.startActivity(shareIntent);
    }

    @Command
    fun shareFile(invoke: Invoke) {
        val args = invoke.parseArgs(ShareFileOptions::class.java)

        println("Sharing file: ${args.file} with type ${args.mimeType}")
        val uri = copyToShareCacheFolder(File(args.file));

        val sendIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.type = args.mimeType
            this.putExtra(Intent.EXTRA_STREAM, uri)
        }

        val shareIntent = Intent.createChooser(sendIntent, null);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.applicationContext?.startActivity(shareIntent);
    }
}
