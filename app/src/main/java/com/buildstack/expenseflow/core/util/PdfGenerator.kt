package com.buildstack.expenseflow.core.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import com.buildstack.expenseflow.domain.model.DashboardData
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfGenerator {

    fun generatePdf(context: Context, data: DashboardData): File? {
        val pdfDocument = PdfDocument()
        
        // Define page dimensions (A4 size approximation in points)
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint()
        var yPosition = 50f

        // Title
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 24f
        paint.color = Color.BLACK
        canvas.drawText("ExpenseFlow - Financial Report", 50f, yPosition, paint)

        yPosition += 30f
        
        // Date
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 14f
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.getDefault())
        canvas.drawText("Generated on: ${dateFormat.format(Date())}", 50f, yPosition, paint)
        
        yPosition += 50f

        // Summary Header
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 18f
        canvas.drawText("Summary", 50f, yPosition, paint)

        yPosition += 30f

        // Summary Details
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 14f
        canvas.drawText("Total Income: $${String.format("%.2f", data.totalIncome)}", 50f, yPosition, paint)
        yPosition += 20f
        canvas.drawText("Total Expenses: $${String.format("%.2f", data.totalExpenses)}", 50f, yPosition, paint)
        yPosition += 20f
        
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("Current Balance: $${String.format("%.2f", data.balance)}", 50f, yPosition, paint)

        yPosition += 50f

        // Transactions Header
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 18f
        canvas.drawText("Recent Transactions", 50f, yPosition, paint)

        yPosition += 30f

        // Transactions List
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 12f
        
        val itemDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        if (data.recentExpenses.isEmpty()) {
            canvas.drawText("No recent transactions.", 50f, yPosition, paint)
        } else {
            // Draw Table Header
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("Date", 50f, yPosition, paint)
            canvas.drawText("Category", 150f, yPosition, paint)
            canvas.drawText("Note", 250f, yPosition, paint)
            canvas.drawText("Amount", 450f, yPosition, paint)
            
            yPosition += 20f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            
            for (expense in data.recentExpenses) {
                // If we reach the bottom of the page, we should ideally start a new page, 
                // but for simplicity in this demo we'll just truncate or fit what we can.
                if (yPosition > 800f) break 
                
                canvas.drawText(itemDateFormat.format(Date(expense.date)), 50f, yPosition, paint)
                canvas.drawText(expense.category.displayName, 150f, yPosition, paint)
                
                // Truncate note if it's too long
                val note = if (expense.note.length > 25) expense.note.take(22) + "..." else expense.note
                canvas.drawText(note, 250f, yPosition, paint)
                
                canvas.drawText("-$${String.format("%.2f", expense.amount)}", 450f, yPosition, paint)
                
                yPosition += 20f
            }
        }

        pdfDocument.finishPage(page)

        // Save PDF to cache directory
        val fileName = "ExpenseFlow_Report_${System.currentTimeMillis()}.pdf"
        val file = File(context.cacheDir, fileName)

        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            pdfDocument.close()
        }
    }
}
