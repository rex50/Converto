package com.rex50.converto.ui.theme.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.rex50.converto.utils.Data


@Composable
fun HomeContent(
    viewModel: HomeContentViewModel = hiltViewModel()
) {

    val currencies by viewModel.currencies.collectAsState()
    val currentCurrency by viewModel.amountTobeConverted.collectAsState()

    ConstraintLayout {

        val (textAmountRefs, listCurrenciesRefs) = createRefs()

        TextField(
            value = currentCurrency,
            placeholder = {
                Text(text = "0.0")
            },
            label = {
                Text(text = "Amount")
            },
            onValueChange = {
                viewModel.convert(it)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Go
            ),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textAmountRefs) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(listCurrenciesRefs) {
                    top.linkTo(textAmountRefs.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            currencies.let {result ->
                when(result) {
                    is Data.Loading -> item {
                        Text(text = "Loading...")
                    }
                    is Data.Successful -> {
                        items(
                            count = result.data.size,
                            key = { index -> result.data[index].currency }
                        ) { index ->
                            result.data[index].apply {
                                Text(
                                    text = "$currency $rate",
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(
                                            vertical = 8.dp,
                                            horizontal = 16.dp
                                        )
                                )

                                convertedCurrency?.takeIf { it != 0.0 }?.let {
                                    Text(
                                        text = it.toString(),
                                        fontSize = 22.sp,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .padding(
                                                bottom = 8.dp,
                                                start = 16.dp,
                                                end = 16.dp
                                            )
                                    )
                                }
                            }
                        }
                    }
                    is Data.Error -> item {
                        Text(text = "Error...")
                    }
                }
            }

        }

    }
}