import {Box, Button, Container, TextField} from "@mui/material";
import {useState} from "react";
import axios from "axios";
import {CREATE_ACCOUNT_URL} from "../Constants";

const NewAccountComponent = () => {
    const [customerIdTextFieldValue, setCustomerIdTextFieldValue] = useState("");
    const [initialCreditTextFieldValue, setInitialCreditTextFieldValue] = useState("");

    const createAccount = () => {
        axios.post(CREATE_ACCOUNT_URL, {
            customerId: customerIdTextFieldValue,
            initialCredit: initialCreditTextFieldValue
        }).catch(error => console.log(error))
            .finally(() => {
                setCustomerIdTextFieldValue("");
                setInitialCreditTextFieldValue("");
            })
    }

    return (
        <Container>
            <Box component="span" sx={{display: "flex", pt: 5, justifyContent: "center"}}>
                <TextField
                    id="outlined-basic"
                    label="Customer ID"
                    variant="outlined"
                    type="number"
                    style={{width: '100%'}}
                    value={customerIdTextFieldValue}
                    onChange={e => setCustomerIdTextFieldValue(e.target.value)}
                />
            </Box>
            <Box component="span" sx={{display: "flex", justifyContent: "center"}}>
                <TextField
                    id="outlined-basic"
                    label="Initial Credit"
                    variant="outlined"
                    type="number"
                    style={{width: '100%'}}
                    value={initialCreditTextFieldValue}
                    onChange={e => setInitialCreditTextFieldValue(e.target.value)}
                />
            </Box>
            <Box component="span" sx={{display: "flex", justifyContent: "center"}}>
                <Button variant="contained" style={{width: '50%'}} onClick={() => createAccount()}>Add</Button>
            </Box>
        </Container>
    )
}

export default NewAccountComponent;