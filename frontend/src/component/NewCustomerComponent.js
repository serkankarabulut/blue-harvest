import {useState} from "react";
import axios from "axios";
import {Box, Button, Container, TextField} from "@mui/material";
import {CREATE_CUSTOMER_URL} from "../Constants";

const NewCustomerComponent = () =>{
    const [nameTextFieldValue, setNameTextFieldValue] = useState("");
    const [surnameTextFieldValue, setSurnameTextFieldValue] = useState("");

    const createCustomer = () => {
        axios.post(CREATE_CUSTOMER_URL, {
            name: nameTextFieldValue,
            surname: surnameTextFieldValue
        }).catch(error => console.log(error))
            .finally(() => {
                setNameTextFieldValue("");
                setSurnameTextFieldValue("");
            })
    }

    return (
        <Container>
            <Box component="span" sx={{display: "flex", pt: 5, justifyContent: "center"}}>
                <TextField
                    id="outlined-basic"
                    label="Name"
                    variant="outlined"
                    type="text"
                    style={{width: '100%'}}
                    value={nameTextFieldValue}
                    onChange={e => setNameTextFieldValue(e.target.value)}
                />
            </Box>
            <Box component="span" sx={{display: "flex", justifyContent: "center"}}>
                <TextField
                    id="outlined-basic"
                    label="Surname"
                    variant="outlined"
                    type="text"
                    style={{width: '100%'}}
                    value={surnameTextFieldValue}
                    onChange={e => setSurnameTextFieldValue(e.target.value)}
                />
            </Box>
            <Box component="span" sx={{display: "flex", justifyContent: "center"}}>
                <Button variant="contained" style={{width: '50%'}} onClick={() => createCustomer()}>Add</Button>
            </Box>
        </Container>
    )
}

export default NewCustomerComponent;