import {Box, Button, Container} from "@mui/material";
import axios from "axios";
import {useEffect, useState} from "react";
import CustomerAccordion from "./CustomerAccordion";
import {BASE_URL} from "../Constants";

const CustomerListComponent = () => {

    const [customerInfo, setCustomerInfo] = useState([]);

    useEffect(()=>{
       getCustomerInfoList();
    }, []);

    const getCustomerInfoList = () => {
        axios.get(BASE_URL)
            .then(response => {
                setCustomerInfo(response.data);
            })
            .catch(error => {
                console.log(error);
            })
    }

    return (
    <Container>
        <Box component="span" sx={{display: "block", p: 10, m: 1,}}>
            {
                customerInfo.map((customer, index) => {
                        return (
                            <CustomerAccordion key={index} customer={customer}/>
                        )
                    }
                )
            }
        </Box>
        <Box component="span" sx={{display: "flex", pr: 10, m: 1, justifyContent: "flex-end"}} >
            <Button variant="contained" onClick={getCustomerInfoList}>Refresh</Button>
        </Box>
    </Container>
    );
}

export default CustomerListComponent;