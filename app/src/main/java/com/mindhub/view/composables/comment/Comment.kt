package com.mindhub.view.composables.comment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Comment
import com.mindhub.view.composables.chips.CommentsChip
import com.mindhub.view.composables.chips.ScoreChip
import com.mindhub.view.layouts.SpacedColumn

@Composable
fun CommentItem(
    comment: Comment,
    isReply: Int? = null,
    onScoreUpdate: (Int, Int) -> Unit,
    onReply: (Int) -> Unit,
    onRemove: (Int, Int?) -> Unit,
    onUpdate: (Int, Int?, String) -> Unit,
    showBestAnswerButton: Boolean,
    onMarkBestAnswer: (Comment) -> Unit,
    onNavigate: (String) -> Unit,
) {
    SpacedColumn(
        spacing = 8,
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            "${BuildConfig.apiPrefix}/static/user/${comment.user}"
                        )
                        .memoryCachePolicy(CachePolicy.DISABLED)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .clickable {
                            onNavigate(comment.user)
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = comment.user,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .clickable {
                            onNavigate(comment.user)
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                if (CurrentUser.user!!.username == comment.user) {
                    Divider(modifier = Modifier
                        .width(2.dp)
                        .height(15.dp)
                    )

                    IconButton(
                        onClick = {
                            onRemove(comment.id, isReply)
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "DeleteCommentButton"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Divider(modifier = Modifier
                        .width(2.dp)
                        .height(15.dp)
                    )

                    IconButton(onClick = {
                        onUpdate(comment.id, isReply, comment.content)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            if (comment.isBestAnswer) {
                Text(
                    text = "✓ Melhor resposta",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(red = 10, green = 133, blue = 36, alpha = 255)
                )
            }
        }

        Text(text = comment.content)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
        ) {
            if (isReply == null) {
                ScoreChip(
                    score = comment.score,
                    userScore = comment.userScore,
                    onIncreaseClick = { onScoreUpdate(comment.id, 1) },
                    onDecreaseClick = { onScoreUpdate(comment.id, -1) },
                )
            }

            if (comment.replies.isNotEmpty()) {
                CommentsChip(commentsQuantity = comment.replies.size)
            }

            if (isReply == null) {
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

            if (showBestAnswerButton) {
                Button(
                    onClick = { onMarkBestAnswer(comment) },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .height(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
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
                    .padding(start = 32.dp, top = 8.dp)
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
                        onScoreUpdate = onScoreUpdate,
                        onRemove = onRemove,
                        onUpdate = onUpdate,
                        isReply = comment.id,
                        showBestAnswerButton = false,
                        onMarkBestAnswer = {},
                        onNavigate = onNavigate
                    )
                }
            }
        }
    }
}