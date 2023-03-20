package com.rex50.converto.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.ui.theme.ConvertoTheme
import com.rex50.converto.utils.CurrencyFormatter

@Composable
fun CurrencyListItem(
    modifier: Modifier = Modifier,
    currency: Currency,
    formattedAmount: String,
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            currency.apply {
                Text(
                    text = "${this.country} (${this.currency})",
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                )

                convertedCurrency?.takeIf { it != 0.0 }?.let {
                    Text(
                        text = formattedAmount,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 8.dp,
                                end = 8.dp
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrencyListItemPreview() {
    ConvertoTheme {
        Surface {
            val currency = Currency(convertedCurrency = 1000.0)
            CurrencyListItem(
                currency = currency,
                formattedAmount = CurrencyFormatter().format(currency.convertedCurrency, currency.currency)
            )
        }
    }
}