package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.pinslist.TripSingleStop

object TripViewTag {
    const val TRIP_NAME = "TRIP_NAME"
    const val TRIP_PLACE = "TRIP_PLACE"
    const val TRIP_DATES = "TRIP_DATES"
}

@Composable
fun TripView(tripSingleStop: TripSingleStop) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TripHeader(tripSingleStop)
            TripRow(tripSingleStop)
        }
    }
}

@Composable
fun TripHeader(tripSingleStop: TripSingleStop) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RoundFlag(tripSingleStop.country)

        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .testTag(TripViewTag.TRIP_NAME),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            text = tripSingleStop.name
        )

        Spacer(Modifier.weight(1.0f))

        Image(
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
                .clickable { },
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = stringResource(
                R.string.pin_list_cd_edit,
                tripSingleStop.name
            )
        )
    }
}

@Composable
fun TripRow(tripSingleStop: TripSingleStop) {
    Row(
        modifier = Modifier.padding(10.dp, 8.dp, 0.dp, 0.dp)
    ) {
        Divider(
            color = colorResource(R.color.primary),
            modifier = Modifier
                .fillMaxHeight()
                .height(20.dp)
                .width(3.dp)
        )

        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .testTag(TripViewTag.TRIP_PLACE),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = tripSingleStop.locationName
        )

        Spacer(Modifier.weight(1.0f))

        Text(
            modifier = Modifier.testTag(TripViewTag.TRIP_DATES),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = tripSingleStop.dateStr
        )
    }
}