import {Accordion, AccordionDetails, AccordionSummary, Box, Container, Typography} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import AccountTransactionAccordion from "./AccountTransactionAccordion";

const CustomerAccordion = ({customer}) => {
    return (
        <Container>
            <Accordion key={customer.customerId}>
                <AccordionSummary
                    expandIcon={<ExpandMore/>}
                >
                    <Typography>Customer ID: {customer.customerId} { " | " }
                        Customer Name: {customer.name + " " + customer.surname}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <AccountTransactionAccordion
                        transactionHistory={customer.transactionHistory ? customer.transactionHistory : []}
                    />
                    {
                        customer.transactionHistory ? (
                            <Box component="span" sx={{display: "flex", pt:2, justifyContent: "center"}}>
                                <Typography style={{fontWeight: "bold"}}>Total Balance: {customer.balance}</Typography>
                            </Box>
                        ) : <Box/>

                    }
                </AccordionDetails>
            </Accordion>
        </Container>
    )
}

export default CustomerAccordion;