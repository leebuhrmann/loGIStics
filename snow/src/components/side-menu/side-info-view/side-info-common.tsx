import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Separator } from "@/components/ui/separator";
import { MockBoundaryData } from "@/mock-data/mock-data";
import {
  AlertMessage,
  SubscribedAlertMessage,
} from "@/services/WebSocketService";
import { subCheckValueAtom } from "@/state/atoms";
import { MagnifyingGlassIcon } from "@radix-ui/react-icons";
import { useRecoilState } from "recoil";

interface SideInfoCommonProps {
  data: Array<AlertMessage | SubscribedAlertMessage | BoundaryData>;
}

interface BoundaryData {
  name: string;
  description: string;
  subscribed: boolean;
}

function isAlertMessage(
  item: AlertMessage | SubscribedAlertMessage | BoundaryData
): item is AlertMessage {
  return (item as AlertMessage).onset !== undefined;
}

function isSubAlertMessage(
  item: AlertMessage | SubscribedAlertMessage | BoundaryData
): item is SubscribedAlertMessage {
  return (item as SubscribedAlertMessage).alert !== undefined;
}

function isBoundaryData(
  item: AlertMessage | SubscribedAlertMessage | BoundaryData
): item is BoundaryData {
  return (
    (item as BoundaryData).name !== undefined &&
    (item as BoundaryData).description !== undefined
  );
}

const options: Intl.DateTimeFormatOptions = {
  year: "numeric",
  month: "long",
  day: "numeric",
  hour: "2-digit",
  minute: "2-digit",
  second: "2-digit",
  hour12: true,
  timeZoneName: "short",
};

export default function SideInfoCommon({ data }: SideInfoCommonProps) {
  const [subCheckValue, setSubCheckValue] = useRecoilState(subCheckValueAtom);

  // Function to handle checkbox changes
  const handleCheckboxChange = () => {
    setSubCheckValue((prevValue: boolean) => !prevValue);
  };

  const filteredData =
    subCheckValue && isBoundaryData(data[0])
      ? data.filter((item) => isBoundaryData(item) && item.subscribed)
      : data;

  return (
    <div id="side-info-common" className="h-full">
      <div className="flex flex-col max-w-full gap-3 h-full">
        <div className="flex w-full items-center space-x-2">
          <Input type="search" placeholder="Search" />
          <Button type="submit" data-testid="search-button">
            <MagnifyingGlassIcon />
          </Button>
        </div>
        <div id="sub_checkbox" className="flex items-center space-x-2">
          <Checkbox
            id="filterSubs"
            checked={subCheckValue}
            onCheckedChange={handleCheckboxChange}
          />
          <label
            htmlFor="filterSubs"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Subscriptions
          </label>
        </div>
        <ScrollArea className="h-5/6 w-full rounded-md border">
          {filteredData.map(
            (
              item: AlertMessage | SubscribedAlertMessage | BoundaryData,
              index: number
            ) => {
              const key = isAlertMessage(item)
                ? `Alert-${item.event}-${index}`
                : `Boundary-${index}`;
              return (
                <div key={key} className="p-2">
                  <DataSelect data={item} index={index}></DataSelect>
                </div>
              );
            }
          )}
        </ScrollArea>
      </div>
    </div>
  );
}

interface DataSelectProps {
  data: AlertMessage | SubscribedAlertMessage | BoundaryData;
  index: number;
}

/**
 * Specifies the styling for the data view of the Alert and Boundary info views
 * @returns html elements for either alert or boundary info view
 */
function DataSelect({ data }: DataSelectProps) {
  // if (isAlertMessage(data)) {
  // Alert Data
  if (isAlertMessage(data)) {
    // console.log("ALERT");
    const issuedDate = new Date(data.onset);
    const expiresDate = new Date(data.expires);

    const issuedFormatted = new Intl.DateTimeFormat("en-US", options).format(
      issuedDate
    );
    const expiresFormatted = new Intl.DateTimeFormat("en-US", options).format(
      expiresDate
    );
    // AlertMessage
    return (
      <>
        <h4>{data.event}</h4>
        <p className="font-semibold">Onset: {issuedFormatted}</p>
        <p className="font-semibold">Expiring: {expiresFormatted}</p>

        <Accordion type="single" collapsible className="w-full">
          <AccordionItem value="item-1">
            <AccordionTrigger className="text-sm text-left font-normal">
              {data.headline}
            </AccordionTrigger>
            <AccordionContent>{data.description}</AccordionContent>
          </AccordionItem>
        </Accordion>
      </>
    );
  } else if (isSubAlertMessage(data)) {
    // SubscribedAlertMessage
    const issuedDate = new Date(data.alert.onset);
    const expiresDate = new Date(data.alert.expires);

    const issuedFormatted = new Intl.DateTimeFormat("en-US", options).format(
      issuedDate
    );
    const expiresFormatted = new Intl.DateTimeFormat("en-US", options).format(
      expiresDate
    );
    return (
      <>
        <h4>{data.alert.event}</h4>
        <p className="font-semibold">Onset: {issuedFormatted}</p>
        <p className="font-semibold">Expiring: {expiresFormatted}</p>
        <p>Boundaries: </p>
        {data.boundaryNames.map((name, index) => (
          <Badge className="mr-0.5" variant="default" key={index}>
            {name}
          </Badge>
        ))}
        <Accordion type="single" collapsible className="w-full">
          <AccordionItem value="item-1">
            <AccordionTrigger className="text-sm text-left font-normal">
              {data.alert.headline}
            </AccordionTrigger>
            <AccordionContent>{data.alert.description}</AccordionContent>
          </AccordionItem>
        </Accordion>
      </>
    );
  } else if (isBoundaryData(data)) {
    // Boundary Data
    return (
      <>
        <h4>Boundary: {data.name}</h4>
        <div id="sub_checkbox" className="flex items-center space-x-2 py-1">
          {data.subscribed ? <Badge variant="default">Subscribed</Badge> : null}
        </div>
        <p className="font-semibold">Description:</p>
        <p>{data.description}</p>
        <Separator />
      </>
    );
  } else {
    return <p>Unknown data type.</p>;
  }
}
