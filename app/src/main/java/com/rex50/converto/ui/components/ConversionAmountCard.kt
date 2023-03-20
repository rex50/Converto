package com.rex50.converto.ui.components


import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rex50.converto.R
import com.rex50.converto.ui.components.core.RoundedButtonWithIcon
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.ui.theme.ConvertoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionAmountCard(
    modifier: Modifier = Modifier,
    label: String,
    selectedCurrency: Currency,
    amount: String,
    onChangeAmount: (String) -> Unit,
    onChangeCurrency: () -> Unit,
    enabled: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.width(16.dp))

            RoundedButtonWithIcon(
                text = "${selectedCurrency.country} (${selectedCurrency.currency})",
                icon = Icons.Default.KeyboardArrowDown,
                onClick = onChangeCurrency
            )
        }

        if (enabled) {
            TextField(
                value = amount,
                placeholder = {
                    Text(
                        text = stringResource(R.string.amount_placeholder),
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                textStyle = MaterialTheme.typography.displayMedium,
                onValueChange = onChangeAmount,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(true)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        } else {
            Text(
                text = amount,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 20.dp,
                        bottom = 32.dp
                    )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ConversionCardPreview() {
    ConvertoTheme {
        Surface {
            ConversionAmountCard(
                label = stringResource(id = R.string.from_currency),
                selectedCurrency = Currency(),
                amount = "",
                onChangeAmount = { },
                onChangeCurrency = { },
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ConversionCardDarkPreview() {
    ConvertoTheme {
        Surface {
            ConversionAmountCard(
                label = stringResource(id = R.string.to_currency),
                selectedCurrency = Currency(),
                amount = "10000",
                enabled = false,
                onChangeAmount = { },
                onChangeCurrency = { },
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}