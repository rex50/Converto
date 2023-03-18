package com.rex50.converto.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.rex50.converto.R
import com.rex50.converto.ui.components.*
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.utils.Data

enum class CardType {
    FROM, TO
}

private object Keys {
    const val LOADER = "loader"
    const val TO_CARD = "To card"
    const val OTHER_CONVERSIONS = "Other conversions"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    viewModel: HomeContentViewModel = hiltViewModel()
) {

    val currencies by viewModel.currencies.collectAsState()
    val amountToBeConverted by viewModel.amountTobeConverted.collectAsState()
    val selectedFromCurrency by viewModel.selectedFromCurrency.collectAsState()
    val selectedToCurrency by viewModel.selectedToCurrency.collectAsState()

    var changingCurrencyForCard by remember { mutableStateOf(CardType.FROM) }
    var showCurrencyChanger by remember { mutableStateOf(false) }
    var showInfoSheet by remember { mutableStateOf(false) }

    ConstraintLayout {

        val (header, conversionRateCard, cardFromCurrency, listCurrenciesRefs) = createRefs()

        Header(
            title = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            showInfoSheet = true
        }

        // Conversion rate text
        SimpleTextBannerCard(
            isVisible = currencies is Data.Successful,
            text = viewModel.getFormattedConversionRate(
                fromCurrency = selectedFromCurrency,
                toCurrency = selectedToCurrency
            ),
            modifier = Modifier
                .padding(
                    vertical = 4.dp,
                    horizontal = 16.dp
                )
                .constrainAs(conversionRateCard) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // From card - where user can enter amount
        ConversionAmountCard(
            label = stringResource(R.string.from_currency),
            selectedCurrency = selectedFromCurrency,
            amount = amountToBeConverted,
            onChangeAmount = {
                viewModel.convert(it)
            },
            onChangeCurrency = {
                changingCurrencyForCard = CardType.FROM
                showCurrencyChanger = true
            },
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
                .constrainAs(cardFromCurrency) {
                    top.linkTo(conversionRateCard.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(listCurrenciesRefs) {
                    top.linkTo(cardFromCurrency.bottom, 8.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            currencies.let { result ->

                item(key = Keys.LOADER) {
                    if (result is Data.Loading) {
                        SimpleContentLoader(
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }

                // To card - where user can see the conversion amount
                item(
                    key = Keys.TO_CARD
                ) {
                    DefaultAnimatedVisibility(result is Data.Successful) {
                        ConversionAmountCard(
                            label = stringResource(R.string.to_currency),
                            selectedCurrency = selectedToCurrency,
                            amount = viewModel.getFormattedCurrencyAmount(
                                amount = selectedToCurrency.convertedCurrency,
                                currencyCode = selectedToCurrency.currency
                            ),
                            enabled = false,
                            onChangeAmount = {},
                            onChangeCurrency = {
                                changingCurrencyForCard = CardType.TO
                                showCurrencyChanger = true
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }


                if (result is Data.Successful) {
                    // Also show other conversions, if user has entered any amount
                    item(
                        key = Keys.OTHER_CONVERSIONS
                    ) {
                        DefaultAnimatedVisibility(
                            isVisible = amountToBeConverted.isNotBlank(),
                            defaultTransitionDuration = 150
                        ) {
                            Text(
                                text = stringResource(R.string.other_conversions, selectedFromCurrency.currency),
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .animateItemPlacement()
                            )
                        }
                    }

                    if (amountToBeConverted.isNotBlank()) {
                        items(
                            count = result.data.size,
                            key = { index -> result.data[index].currency }
                        ) { index ->
                            result.data[index].apply {
                                CurrencyListItem(
                                    currency = this,
                                    formattedAmount = viewModel.getFormattedCurrencyAmount(
                                        amount = convertedCurrency,
                                        currencyCode = currency
                                    ),
                                    modifier = Modifier
                                        .animateItemPlacement()
                                )
                            }
                        }
                    }
                }

                item {
                    DefaultAnimatedVisibility(result is Data.Error) {
                        if(result is Data.Error) {
                            ErrorCard(message = result.message) {
                                viewModel.fetchCurrencies()
                            }
                        }
                    }
                }
            }
        }
    }

    // Bottom sheet menu for currency selection
    if (showCurrencyChanger) {
        val currenciesList = if (currencies is Data.Successful)
            (currencies as Data.Successful<List<Currency>>).data else listOf(Currency())

        val selectedCurrency = if (changingCurrencyForCard == CardType.FROM)
            selectedFromCurrency else selectedToCurrency

        CurrencySelector(
            selectedCurrency = selectedCurrency,
            currencies = currenciesList,
            onCurrencySelected = {
                showCurrencyChanger = false
                if (changingCurrencyForCard == CardType.FROM)
                    viewModel.changeSelectedFromCurrency(it)
                else
                    viewModel.changeSelectedToCurrency(it)

            },
            onDismissRequest = {
                showCurrencyChanger = false
            }
        )
    }

    if(showInfoSheet) {
        AboutSheet() {
            showInfoSheet = false
        }
    }
}