package com.mindhub.view.composables.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Comment
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.chips.CommentsChip
import com.mindhub.view.composables.chips.ScoreChip
import com.mindhub.view.layouts.SpacedColumn

@Composable
fun CommentItem(
    comment: Comment,
    isReply: Boolean = false,
    onScoreUpdate: (Int, Int) -> Unit,
    onReply: (Int) -> Unit,
) {
    SpacedColumn(
        spacing = 8,
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Text(
                    text = comment.username,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            if (comment.isBestAnswer) {
                Text(
                    text = "✅ Melhor resposta",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        Text(text = comment.content)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!isReply) {
                ScoreChip(
                    score = comment.score,
                    onIncreaseClick = { onScoreUpdate(comment.id, comment.score + 1) },
                    onDecreaseClick = { onScoreUpdate(comment.id, comment.score - 1) },
                )
            }

            if (comment.replies.isNotEmpty()) {
                CommentsChip(commentsQuantity = comment.replies.size)
            }

            if (!isReply) {
                Button(
                    onClick = {
                        onReply(comment.id)
                    },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .weight(1.0f)
                        .height(32.dp)
                ) {
                    Text(text = "Responder", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }

        if (comment.replies.isNotEmpty()) {
            val lineColor = MaterialTheme.colorScheme.surfaceVariant

            SpacedColumn(
                spacing = 8,
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp)
                    .drawBehind {
                        drawLine(
                            color = lineColor,
                            strokeWidth = 2.0f,
                            start = Offset(-16.dp.toPx(), 0.0f),
                            end = Offset(-16.dp.toPx(), size.height)
                        )
                    }
            ) {
                for (reply in comment.replies) {
                    CommentItem(
                        comment = reply,
                        onReply = onReply,
                        isReply = true,
                        onScoreUpdate = onScoreUpdate,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommentPreview() {
    val comment1 = Comment(
        id = 0,
        postId = 0,
        username = "teste76",
        content = "Massa demais tu é doido",
        isBestAnswer = false,
        score = 4,
        userScore = 0,
        replies = mutableListOf()
    )

    val comment2 = Comment(
        id = 0,
        postId = 0,
        username = "jooa_xd",
        content = "Mussum Ipsum, cacilds vidis litro abertis.  Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis. Nulla id gravida magna, ut semper sapien. Manduma pindureta quium dia nois paga. Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis.",
        isBestAnswer = false,
        score = -9,
        userScore = 0,
        replies = mutableListOf(comment1)
    )

    val comment3 = Comment(
        id = 0,
        postId = 0,
        username = "teste76",
        content = "Suco de cevadiss deixa as pessoas mais interessantis. Mé faiz elementum girarzis, nisi eros vermeio. Quem num gosta di mim que vai caçá sua turmis! Praesent vel viverra nisi. Mauris aliquet nunc non turpis scelerisque, eget.",
        isBestAnswer = true,
        score = 4,
        userScore = 0,
        replies = mutableListOf(
            comment1,
            comment2
        )
    )

    MindHubTheme {
        SpacedColumn(
            spacing = 16,
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            CommentItem(
                comment = comment3,
                onScoreUpdate = { it1, it2 -> },
            ) {}
        }
    }
}