package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ytubebuddy.ctefilters.models.Filters
import com.ytubebuddy.ctefilters.models.FiltersX
import com.ytubebuddy.ctefilters.models.Main
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    FilterScreen()
}

@Composable
fun FilterScreen() {

    val filters = getFiltersFromJson("instafilters.json")

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {

        /*AppTopBar("Filter", onClick = {
            // Toast.makeText(context, "Filter", Toast.LENGTH_SHORT).show()
            if (activity != null) {
                activity.finish()
            }
        })*/

        JsonListScreen(filters.filters)
    }
}

@Composable
fun loadFilters(): FiltersX {
    val filters = getFiltersFromJson("instafilters.json")
    return filters.filters
}

@Composable
fun getFiltersFromJson(fileName: String): Filters {
    val jsonString = readJsonFile(fileName) // Get the JSON as a String
    return Json.decodeFromString<Filters>(jsonString) // Explicitly specify the type
}

@Composable
fun JsonListScreen(jsonData: FiltersX) {
    var mainData by remember { mutableStateOf<FiltersX>(jsonData) }
    var selectedItem by remember { mutableStateOf<Main?>(null) }
    var count by remember { mutableStateOf<Int?>(0) }
    var ischecked by remember { mutableStateOf<Boolean?>(false) }
    var ismodelchecked by remember { mutableStateOf<Boolean?>(false) }


    Column(modifier = Modifier.background(color = Color.White)) {
        Row(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxHeight()
            ) {
                LazyColumn {
                    items(mainData.mainList) { item ->
                        var isSelected = item == selectedItem
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clickable { selectedItem = item } // Handle item click here
                                .background(
                                    if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background
                                )
                        ) {
                            count = 0
                            if (item.title == mainData.mainList[1].title) {
                                val truemakeItems = mainData.makesModels.filter { it.isSelected }
                                count = truemakeItems.size
                            } else if (item.title == mainData.mainList[2].title) {
                                count = mainData.age.filter { it.isSelected }.size
                            } else if (item.title == mainData.mainList[3].title) {
                                count = mainData.fuel.filter { it.isSelected }.size
                            } else if (item.title == mainData.mainList[4].title) {
                                count = mainData.owners.filter { it.isSelected }.size
                            } else if (item.title == mainData.mainList[0].title) {
                                count = mainData.city.filter { it.isSelected }.size
                            } else if (item.title == mainData.mainList[5].title) {
                                count = mainData.bestMatch.filter { it.isSelected }.size
                            }

                            var txttitle = ""
                            if (count!! > 0) {
                                txttitle = item.title + " (" + count + ")"
                            } else {
                                txttitle = item.title
                            }
                            Text(
                                text = txttitle,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterStart),
                                color = if (item == selectedItem) MaterialTheme.colors.background else MaterialTheme.colors.primary
                            )
                            Divider(
                                color = Color(0xFFE9E9E9),
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                            )

                        }
                    }
                    selectedItem = mainData.mainList[0]
                }
                Divider(
                    color = Color(0xFFE9E9E9),
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )

            }

            Divider(
                color = Color(0xFFE9E9E9),
                thickness = 1.dp,
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )

            selectedItem?.let { main ->

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                    //modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    //MySearch()
                    if (main.title == mainData.mainList[1].title) {


                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(mainData.makesModels) { index, item ->
                                //RHSItem(item)
                                ischecked = item.isSelected

                                Row(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            ischecked = !item.isSelected!!
                                            item.isSelected = ischecked!!
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = item.title,
                                        modifier = Modifier
                                            .padding(7.dp)
                                            .weight(1f)
                                    )

                                    Checkbox(
                                        checked = ischecked!!,
                                        onCheckedChange = { newChecked ->
                                            ischecked = newChecked
                                            item.isSelected = newChecked
                                        }
                                    )
                                }

                                if (item.isSelected) {
                                    Column(modifier = Modifier.padding(start = 10.dp)) {
                                        item.models.forEach { model ->
                                            ismodelchecked = model.isSelected

                                            Row(
                                                modifier = Modifier
                                                    .height(35.dp)
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        ismodelchecked = !model.isSelected!!
                                                        model.isSelected = ismodelchecked!!
                                                    },
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = model.title,
                                                    modifier = Modifier
                                                        .padding(7.dp)
                                                        .weight(1f)
                                                )
                                                Checkbox(
                                                    checked = ismodelchecked!!,
                                                    onCheckedChange = { newChecked ->
                                                        ismodelchecked = newChecked
                                                        model.isSelected = newChecked
                                                    },
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    } else if (main.title == mainData.mainList[2].title) {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(mainData.age) { index, item ->
                                //RHSItem(item)
                                ischecked = item.isSelected
                                Row(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            ischecked = !item.isSelected!!
                                            item.isSelected = ischecked!!
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.title,
                                        modifier = Modifier
                                            .padding(7.dp)
                                            .weight(1f)
                                    )

                                    Checkbox(
                                        checked = ischecked!!,
                                        onCheckedChange = { newChecked ->
                                            ischecked = newChecked
                                            item.isSelected = newChecked
                                        }
                                    )

                                }
                            }
                        }
                    } else if (main.title == mainData.mainList[3].title) {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(mainData.fuel) { index, item ->
                                //RHSItem(item)
                                ischecked = item.isSelected
                                //var checked by remember { mutableStateOf(item.isSelected) }
                                Row(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            ischecked = !item.isSelected!!
                                            item.isSelected = ischecked!!
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.title,
                                        modifier = Modifier
                                            .padding(7.dp)
                                            .weight(1f)
                                    )
                                    Checkbox(
                                        checked = ischecked!!,
                                        onCheckedChange = { newChecked ->
                                            ischecked = newChecked
                                            item.isSelected = newChecked
                                        }

                                    )
                                }
                            }
                        }
                    } else if (main.title == mainData.mainList[4].title) {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(mainData.owners) { index, item ->
                                //RHSItem(item)
                                ischecked = item.isSelected
                                //var checked by remember { mutableStateOf(item.isSelected) }
                                Row(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            ischecked = !item.isSelected!!
                                            item.isSelected = ischecked!!
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.title,
                                        modifier = Modifier
                                            .padding(7.dp)
                                            .weight(1f)
                                    )

                                    Checkbox(
                                        checked = ischecked!!,
                                        onCheckedChange = { newChecked ->
                                            ischecked = newChecked
                                            item.isSelected = newChecked
                                        }

                                    )
                                }
                            }
                        }
                    } else if (main.title == mainData.mainList[0].title) {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(mainData.city) { index, item ->
                                //RHSItem(item)
                                ischecked = item.isSelected
                                //var checked by remember { mutableStateOf(item.isSelected) }
                                Row(
                                    modifier = Modifier
                                        .height(35.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            ischecked = !item.isSelected!!
                                            item.isSelected = ischecked!!
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = item.title,
                                        modifier = Modifier
                                            .padding(7.dp)
                                            .weight(1f)
                                    )


                                    Checkbox(
                                        checked = ischecked!!,
                                        onCheckedChange = { newChecked ->
                                            ischecked = newChecked
                                            item.isSelected = newChecked
                                        }

                                    )
                                }
                            }
                        }
                    } else if (main.title == mainData.mainList[5].title) {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(mainData.bestMatch) { index, item ->
                                //RHSItem(item)
                                ischecked = item.isSelected
                                //var checked by remember { mutableStateOf(item.isSelected) }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(35.dp)
                                        .clickable {
                                            ischecked = !item.isSelected!!
                                            item.isSelected = ischecked!!
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.title,
                                        modifier = Modifier
                                            .padding(7.dp)
                                            .weight(1f)
                                    )
                                    Checkbox(
                                        checked = ischecked!!,
                                        onCheckedChange = { newChecked ->
                                            ischecked = newChecked
                                            item.isSelected = newChecked
                                        }

                                    )
                                }
                            }
                        }
                    }


                }
            }
        }

        Divider(
            color = Color(0xFFE9E9E9),
            thickness = 1.dp,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    //var resetdata = readJsonfilterFile("instafilters.json")
                    //mainData = loadFilters()

                },
                content = {
                    Text("Reset")
                }
            )

            Spacer(
                modifier = Modifier
                    .padding(8.dp)
            )


            GradientButtonPreview1("Apply", modifier = Modifier.weight(1f))
        }


    }
}

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    startColor: Color,
    endColor: Color,
    strokeColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


@Composable
fun GradientButtonPreview1(text: String, modifier: Modifier = Modifier) {


    Button(
        modifier = modifier,
        onClick = {},
        content = {
            Text(
                text = text
            )
        }
    )
}
