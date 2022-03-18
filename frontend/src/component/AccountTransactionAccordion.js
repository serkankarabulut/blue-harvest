import {Accordion, AccordionDetails, AccordionSummary, Typography} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";

const AccountTransactionAccordion = ({transactionHistory}) => {
    return (
        <div>
            {
                transactionHistory.map((history, index)=>{
                    return(
                        <Accordion key={index}>
                            <AccordionSummary
                                expandIcon={<ExpandMore/>}
                                aria-controls="panel1a-content"
                                id="panel1a-content"
                            >
                                <Typography>Account ID: {history.accountId}</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                {
                                    history.transactionList.map((credit, index)=>{
                                        return(
                                            <Typography key={index}>{credit}</Typography>
                                        )
                                    })
                                }
                            </AccordionDetails>
                        </Accordion>
                    )
                })
            }
        </div>
    )
}

export default AccountTransactionAccordion;
