import {Box, Button, Container, TextField} from "@mui/material";
import CustomerAccordion from "./CustomerAccordion";
import axios from "axios";
import {useState} from "react";
import {BASE_URL} from "../Constants";

const SearchComponent = () => {

    const [customerInfo, setCustomerInfo] = useState(null);
    const [customerIdTextFieldValue, setCustomerIdTextFieldValue] = useState("");

    const getCustomerInfo = () => {
        console.log(customerIdTextFieldValue);
        axios.get(BASE_URL + customerIdTextFieldValue)
            .then(response => {
                setCustomerInfo(response.data);
            })
            .catch(error => {
                console.log(error);
            })
            .finally(()=>{
                setCustomerIdTextFieldValue("");
            })
    }

    return (
        <Container >
            <Box component="span" sx={{display: "flex", pt:5, justifyContent: "center"}}>
                <TextField
                    id="outlined-basic"
                    label="Customer ID"
                    variant="outlined"
                    type="number"
                    style={{width: '100%'}}
                    value={customerIdTextFieldValue}
                    onChange={e=>setCustomerIdTextFieldValue(e.target.value)}
                />
            </Box>
            <Box component="span" sx={{display: "flex", justifyContent: "center"}}>
                <Button variant="contained" style={{width: '50%'}} onClick={()=>{
                    getCustomerInfo();
                }}>Find</Button>
            </Box>
            {
               customerInfo ? (<CustomerAccordion customer={customerInfo}/>) : <Box/>
            }
        </Container>
    )
}

export default SearchComponent;