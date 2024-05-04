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
  data: any;
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
  // Function to handle checkbox changes
  const handleCheckboxChange = () => {
    setSubCheckValue((prevValue: boolean) => !prevValue);
  };
  const [subCheckValue, setSubCheckValue] = useRecoilState(subCheckValueAtom);

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
            checked={!subCheckValue}
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
          {/* Loop through each item in data, output DataSelect */}
          {data.map((item: AlertMessage | MockBoundaryData, index: number) => (
            <div key={`Alert-${data.title}-${index}`} className="p-2">
              <DataSelect data={item} index={index}></DataSelect>
            </div>
          ))}
        </ScrollArea>
      </div>
    </div>
  );
}

interface DataSelectProps {
  data: AlertMessage | SubscribedAlertMessage | MockBoundaryData;
  index: number;
}
/**
 * Specifies the styling for the data view of the Alert and Boundary info views
 * @returns html elements for either alert or boundary info view
 */
function DataSelect({ data, index }: DataSelectProps) {
  if (!("sub" in data)) {
    // Alert Data
    if (!("boundaryIds" in data)) {
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
    } else {
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
    }
  } else {
    // Boundary Data
    return (
      <>
        <h4>{data.title}</h4>
        <div id="sub_checkbox" className="flex items-center space-x-2 py-1">
          <Checkbox id="boundarySubs" defaultChecked={data.sub} />
          <label
            htmlFor="boundarySubs"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Subscribe
          </label>
        </div>
        {/* Loop through the header list */}
        {data.header.map((headerItem: string, headerIndex: number) => (
          <p
            key={`Boundary-${data.header}-${headerIndex}`}
            className="font-semibold"
          >
            {headerItem}
          </p>
        ))}
        {/* Loop through the body list */}
        {data.body.map((bodyItem: string, bodyIndex: number) => (
          <p key={`Boundary-${data.body}-${bodyIndex}`}>{bodyItem}</p>
        ))}
        <Separator />
      </>
    );
  }
}
