package com.codemave.reminderapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.systemBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codemave.reminderapp.Data.Entity.Reminder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController


@Composable
fun Home(
    navController: NavController,
    userID: Int
) {
    var viewModel: HomeViewModel

    Scaffold(
        modifier = Modifier.padding(bottom = 0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "addReminder/"+userID.toString()) },
                contentColor = Color.DarkGray,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddAlert,
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
                navController = navController,
                userID
            )

            viewModel = viewModel()
            val viewState by viewModel.state.collectAsState()
            Column(modifier = Modifier.systemBarsPadding().fillMaxWidth()) {
                ReminderList(
                    list = viewState.reminders,
                    viewModel = viewModel,
                    navController = navController,
                    userID = userID
                )
            }
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color,
    navController: NavController,
    userID: Int
) {
    TopAppBar(
        title = {
            Text(
                text = "All reminders",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {navController.navigate(route = "login")} ) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "")

            }
            IconButton(onClick = { navController.navigate("profil/"+userID.toString()) }) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
            }
        }
    )
}

@Composable
private fun ReminderList(
    list: List<Reminder>,
    viewModel: HomeViewModel,
    navController: NavController,
    userID: Int
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            if(item.creator_id == userID) {
                ReminderListItem(
                    reminder = item,
                    viewModel = viewModel,
                    onClick = {},
                    modifier = Modifier.fillParentMaxWidth(),
                    navController = navController,
                )
            }
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    viewModel: HomeViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, icon, reminderTitle, edit, date) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        when (reminder.icon){
            1 -> Icon(
                    imageVector = Icons.Default.AccessAlarm,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                            start.linkTo(parent.start,10.dp)
                        }
                 )
            2 -> Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                            start.linkTo(parent.start,10.dp)
                        }
                 )
            3 -> Icon(
                    imageVector = Icons.Default.AccountTree,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                            start.linkTo(parent.start,10.dp)
                        }
                 )
            4 -> Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                            start.linkTo(parent.start,10.dp)
                        }
                 )
            5 -> Icon(
                    imageVector = Icons.Default.Assistant,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                        .constrainAs(icon) {
                            top.linkTo(parent.top, 10.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                            start.linkTo(parent.start,10.dp)
                        }
                 )
        }

        // title
        Text(
            text = reminder.message,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTitle) {
                linkTo(
                    start = icon.end,
                    end = edit.start,
                    startMargin = 10.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // date
        Text(
            text = reminder.reminder_time,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(date) {
                linkTo(
                    start = icon.end,
                    end = edit.start,
                    startMargin = 10.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(reminderTitle.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // icon edit
        IconButton(
            onClick = { navController.navigate(route = "editreminder/"+reminder.reminderId.toString()) },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(edit) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = ""
            )
        }
    }
}
